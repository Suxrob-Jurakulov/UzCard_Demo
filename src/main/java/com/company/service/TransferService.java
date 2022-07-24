package com.company.service;

import com.company.dto.transfer.TransferCreateDTO;
import com.company.dto.transfer.TransferDTO;
import com.company.dto.transfer.TransferResponseDTO;
import com.company.dto.transfer.TransferStatusDTO;
import com.company.entity.CardEntity;
import com.company.entity.CompanyEntity;
import com.company.entity.TransactionsEntity;
import com.company.entity.TransferEntity;
import com.company.enums.*;
import com.company.exp.ItemNotFoundException;
import com.company.repository.CardRepository;
import com.company.repository.TransactionRepository;
import com.company.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferService {

    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private CardService cardService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    private final double uzCardServicePercentage = 0.5;


    public TransferResponseDTO create(TransferCreateDTO transferDTO) {


        CardEntity fromCard = cardService.get(transferDTO.getFromCard());
        CardEntity toCard = cardService.get(transferDTO.getToCard());
        CompanyEntity company = companyService.get(transferDTO.getCompanyId());

        if (!fromCard.getStatus().equals(GeneralStatus.ACTIVE)) {
            return new TransferResponseDTO("failed", "Your Card is not active");
        }

        if (!toCard.getStatus().equals(GeneralStatus.ACTIVE)) {
            return new TransferResponseDTO("failed", "The recipient's card is not active");
        }

        // 10,000
        //  company.getServicePercentage()  0.3
        //  0.4 - uzCard
        double service_percentage = uzCardServicePercentage + company.getServicePercentage(); // 0.7
        double service_amount = (transferDTO.getAmount() * service_percentage) / 100; // 70
        double total_amount = transferDTO.getAmount() + service_amount; // 10,070

        if (fromCard.getBalance() < total_amount) {
            return new TransferResponseDTO("failed", "Not enough money");
        }

        TransferEntity entity = new TransferEntity();
        entity.setFromCardId(fromCard.getId());
        entity.setToCardId(toCard.getId());
        entity.setTotalAmount((long) total_amount);
        entity.setAmount(transferDTO.getAmount());
        entity.setServiceAmount((long) service_amount);
        entity.setServicePercentage(service_percentage);
        entity.setStatus(TransferStatus.IN_PROGRESS);
        entity.setCompanyId(transferDTO.getCompanyId());
        transferRepository.save(entity);

        TransferDTO dto = getDTO(entity);
        dto.setHiddenCardNum(fromCard.getHiddenNumber());
        return new TransferResponseDTO("Created", "The transfer is ready for confirmation", dto);
    }

    public TransferEntity get(String id) {
        return transferRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Something went wrong!");
        });
    }

    public void finishTransfer(String id) {
        TransferEntity entity = get(id);
        cardRepository.fromCardCredit(entity.getFromCardId(), entity.getTotalAmount());
        cardRepository.toCardDebit(entity.getToCardId(), entity.getAmount());
        CardEntity uzCard = cardRepository.findByCompanyRole(GeneralRole.ROLE_UZ_CARD);

        Double serviceAmount = Double.valueOf(entity.getServiceAmount());

        CompanyEntity company = companyService.get(entity.getCompanyId());
        Long companyAmount = (long) (entity.getAmount() * company.getServicePercentage()) / 100;
        cardRepository.debitCompanyCard(companyAmount, company.getCardId());

        Long uzCardAmount = (long) (serviceAmount - companyAmount);
        cardRepository.debitToUzCard(uzCardAmount, uzCard.getId());

        TransactionsEntity fromCardAction = new TransactionsEntity();
        fromCardAction.setCardId(entity.getFromCardId());
        fromCardAction.setAmount(entity.getTotalAmount());
        fromCardAction.setType(TransactionType.CREDIT);
        fromCardAction.setTransferId(entity.getId());
        fromCardAction.setStatus(TransactionStatus.SUCCESS);
        transactionRepository.save(fromCardAction);

        TransactionsEntity toCardAction = new TransactionsEntity();
        toCardAction.setCardId(entity.getToCardId());
        toCardAction.setAmount(entity.getAmount());
        toCardAction.setType(TransactionType.DEBIT);
        toCardAction.setTransferId(entity.getId());
        toCardAction.setStatus(TransactionStatus.SUCCESS);
        transactionRepository.save(toCardAction);

        TransactionsEntity companyAction = new TransactionsEntity();
        companyAction.setCardId(company.getCardId());
        companyAction.setAmount(companyAmount);
        companyAction.setType(TransactionType.DEBIT);
        companyAction.setTransferId(entity.getId());
        companyAction.setStatus(TransactionStatus.SUCCESS);
        transactionRepository.save(companyAction);

        TransactionsEntity uzCardAction = new TransactionsEntity();
        uzCardAction.setCardId(uzCard.getId());
        uzCardAction.setAmount(uzCardAmount);
        uzCardAction.setType(TransactionType.DEBIT);
        uzCardAction.setTransferId(entity.getId());
        uzCardAction.setStatus(TransactionStatus.SUCCESS);
        transactionRepository.save(uzCardAction);

        transferRepository.updateStatus(TransferStatus.SUCCESS, entity.getId());
    }




    public TransferDTO getDTO(TransferEntity entity) {
        TransferDTO dto = new TransferDTO();
        dto.setId(entity.getId());
        dto.setFromCardId(entity.getFromCardId());
        dto.setToCardId(entity.getToCardId());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setAmount(entity.getAmount());
        dto.setServiceAmount(entity.getServiceAmount());
        dto.setServicePercentage(entity.getServicePercentage());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setStatus(entity.getStatus());
        dto.setCompanyId(entity.getCompanyId());
        return dto;
    }

    public String changeStatus(TransferStatusDTO dto) {
        TransferEntity entity = get(dto.getId());
        if (entity.getStatus().name().equals("IN_PROGRESS")){
            if (dto.getStatus().name().equals("SUCCESS")) {
                transferRepository.changeStatus(dto.getStatus(), dto.getId());
                return "To'lov amalga oshirildi";
            }
            if (dto.getStatus().name().equals("FAILED")) {
                transferRepository.changeStatus(dto.getStatus(), dto.getId());
                return "To'lov bekor qilindi";
            }
        }
        return "Something went wrong";
    }


}
