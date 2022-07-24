package com.company.service;

import com.company.dto.card.CardDTO;
import com.company.dto.transaction.TransactionDTO;
import com.company.entity.TransactionsEntity;
import com.company.enums.TransactionType;
import com.company.exp.ItemNotFoundException;
import com.company.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CardService cardService;

    public PageImpl<TransactionDTO> getPageByCardId(String cardId, int page, int size) {
        get(cardId);
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<TransactionsEntity> all = transactionRepository.getPaginationByCardId(cardId, pageable);
        List<TransactionDTO> list = new LinkedList<>();
        all.forEach(transactions -> list.add(getDTO(transactions)));
        return new PageImpl<>(list, pageable, all.getTotalElements());
    }

    public TransactionsEntity get(String id){
        return transactionRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Transfer not found!");
        });
    }

    public PageImpl<TransactionDTO> getPaginationByCardNum(String cardNum, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<TransactionsEntity> all = transactionRepository.findByCardNum(cardNum, pageable);
        List<TransactionDTO> list = new LinkedList<>();
        all.forEach(transactions -> list.add(getDTO(transactions)));
        return new PageImpl<>(list, pageable, all.getTotalElements());
    }

    private TransactionDTO getDTO(TransactionsEntity entity){
        TransactionDTO dto = new TransactionDTO();
        dto.setId(entity.getId());
        dto.setCardId(entity.getCardId());
        dto.setAmount(entity.getAmount());
        dto.setStatus(entity.getStatus());
        dto.setTransferId(entity.getTransferId());
        dto.setType(entity.getType());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public PageImpl<TransactionDTO> getPaginationByCompanyId(String companyId, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<TransactionsEntity> all = transactionRepository.findByCompanyId(companyId, pageable);
        List<TransactionDTO> list = new LinkedList<>();
        all.forEach(transactions -> list.add(getDTO(transactions)));
        return new PageImpl<>(list, pageable, all.getTotalElements());
    }

    public PageImpl<TransactionDTO> getPaginationByCardPhone(String cardPhone, int page, int size) {
        boolean existCard = cardService.findByPhone(cardPhone);
        if (!existCard){
            throw new ItemNotFoundException("Card not found");
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<TransactionsEntity> all = transactionRepository.findByCardPhone(cardPhone, pageable);
        List<TransactionDTO> list = new LinkedList<>();
        all.forEach(transactions -> list.add(getDTO(transactions)));
        return new PageImpl<>(list, pageable, all.getTotalElements());
    }

    public PageImpl<TransactionDTO> getCreditByCardId(String cardId, int page, int size) {
        get(cardId);
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<TransactionsEntity> all = transactionRepository.findByCardIdAndType(cardId, TransactionType.CREDIT, pageable);
        List<TransactionDTO> list = new LinkedList<>();
        all.forEach(transactions -> list.add(getDTO(transactions)));
        return new PageImpl<>(list, pageable, all.getTotalElements());

    }
    public PageImpl<TransactionDTO> getDebitByCardId(String cardId, int page, int size) {
        cardService.get(cardId);
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<TransactionsEntity> all = transactionRepository.findByCardIdAndType(cardId, TransactionType.DEBIT, pageable);
        List<TransactionDTO> list = new LinkedList<>();
        all.forEach(transactions -> list.add(getDTO(transactions)));
        return new PageImpl<>(list, pageable, all.getTotalElements());

    }

    public List<TransactionDTO> getLastMonthByCardNum(String cardNum) {
        boolean existCard = cardService.checkByCardNumber(cardNum);
        if (!existCard){
            throw new ItemNotFoundException("Card not found");
        }
       List<TransactionsEntity> all = transactionRepository.getLastMonthByCardNum(cardNum, LocalDateTime.now().minusMonths(1));
       return all.stream().map(this::getDTO).collect(Collectors.toList());
    }
}
