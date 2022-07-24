package com.company.service;

import com.company.dto.card.CardDTO;
import com.company.dto.card.CardFilterDTO;
import com.company.dto.card.CardPhoneDTO;
import com.company.dto.card.CardStatusDTO;
import com.company.dto.client.ClientDTO;
import com.company.entity.CardEntity;
import com.company.enums.GeneralRole;
import com.company.enums.GeneralStatus;
import com.company.exp.ItemNotFoundException;
import com.company.repository.CardRepository;
import com.company.repository.custome.CustomCardRepository;
import com.company.util.CardNumberGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public record CardService(CardRepository cardRepository,
                          CardNumberGenerator cardNumberGenerator,
                          AuthService authService,
                          CustomCardRepository customCardRepository) {

    public CardDTO create(CardDTO dto) {
        CardEntity entity = new CardEntity();
        entity.setBalance(dto.getBalance());
        entity.setClientId(dto.getClientId());
        entity.setExpiredDate(LocalDateTime.now().plusYears(4));
        String cardNum = cardNumberGenerator.generate("8600", 16);
        entity.setNumber(cardNum);
        entity.setHiddenNumber(cardNum.substring(0,4) + " **** **** " + cardNum.substring(12));
        entity.setCompanyId(authService.getCurrentUser().getId());
        entity.setStatus(GeneralStatus.NOT_ACTIVE);
        cardRepository.save(entity);

        dto.setId(entity.getId());
        dto.setNumber(entity.getNumber());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setExpiredDate(entity.getExpiredDate());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public void assignPhone(CardPhoneDTO dto) {
        get(dto.getId());
        cardRepository.updatePhone(dto.getPhone(), dto.getId());
    }

    public CardEntity get(String id) {
        return cardRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Card not found!");
        });
    }

    public String changeStatus(CardStatusDTO dto) {
        get(dto.getId());
        GeneralRole role = authService.getCurrentUser().getRole();
        if (role.equals(GeneralRole.ROLE_PAYMENT)) {
            cardRepository.changeStatus(GeneralStatus.BLOCK, dto.getId());
            return "Card blocked";
        }
        cardRepository.changeStatus(dto.getStatus(), dto.getId());
        return "Card successfully " + dto.getStatus().name();
    }

    public CardDTO getCardById(String id) {
        CardEntity entity = get(id);
        roleCheck(entity.getCompanyId());
        return getDTO(entity);
    }

    public List<CardDTO> getCardListByPhone(String phone) {
        List<CardEntity> entityList = cardRepository.findByPhone(phone);
        List<CardDTO> list = new LinkedList<>();
        GeneralRole role = authService.getCurrentUser().getRole();
        entityList.forEach(entity -> {
            if (role.equals(GeneralRole.ROLE_PAYMENT)) {
                list.add(getDTO(entity));
            } else {
                CardDTO dto = checkCompanyRole(entity.getCompanyId(), entity);
                if (dto != null) {
                    list.add(dto);
                }
            }
        });
        return list;
    }


    private CardDTO checkCompanyRole(String cId, CardEntity entity) {
        String companyId = authService.getCurrentUser().getId();
        if (Objects.equals(cId, companyId)) {
            return getDTO(entity);
        }
        return null;
    }


    public CardDTO getDTO(CardEntity entity) {
        CardDTO dto = new CardDTO();
        dto.setId(entity.getId());
        dto.setNumber(entity.getNumber());
        dto.setBalance(entity.getBalance());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPhone(entity.getPhone());
        dto.setExpiredDate(entity.getExpiredDate());
        dto.setStatus(entity.getStatus());

        ClientDTO client = new ClientDTO();
        client.setId(entity.getClientId());
        client.setName(entity.getClient().getName());
        client.setSurname(entity.getClient().getSurname());
        dto.setClient(client);
        return dto;
    }

    public List<CardDTO> getCardListByClientId(String clientId) {
        GeneralRole role = authService.getCurrentUser().getRole();
        List<CardDTO> list = new LinkedList<>();
        List<CardEntity> entityList;
        if (role.equals(GeneralRole.ROLE_PAYMENT)) {
            entityList = cardRepository.findByClientId(clientId);
        } else {
            String cId = authService.getCurrentUser().getId();
            entityList = cardRepository.findByClientIdAndCompanyId(clientId, cId);
        }
        entityList.forEach(entity -> {
            list.add(getDTO(entity));
        });
        return list;
    }

    public CardDTO getCardByNumber(String num) {
        Optional<CardEntity> optional = cardRepository.findByNumber(num);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Card not found!");
        }
        CardEntity entity = optional.get();
        roleCheck(entity.getCompanyId());
        return getDTO(entity);
    }

    private void roleCheck(String cId) {
        GeneralRole role = authService.getCurrentUser().getRole();
        String companyId = authService.getCurrentUser().getId();

        if (role.equals(GeneralRole.ROLE_BANK)) {
            if (!Objects.equals(cId, companyId)) {
                throw new ItemNotFoundException("Bu card boshqa bankka tegishli!");
            }
        }
    }

    public CardDTO getCardBalanceByNumber(String num) {
        Optional<CardEntity> optional = cardRepository.findByNumber(num);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Card not found!");
        }
        CardEntity entity = optional.get();
        roleCheck(entity.getCompanyId());

        CardDTO dto = new CardDTO();
        dto.setBalance(entity.getBalance());
        return dto;
    }

    public List<CardDTO> getByFilter(CardFilterDTO dto) {
        List<CardDTO> list = new LinkedList<>();
        customCardRepository.filter(dto).forEach(entity -> {
            list.add(getDTO(entity));
        });
        return list;
    }

    public boolean findByPhone(String cardPhone) {
        List<CardEntity> card = cardRepository.findByPhone(cardPhone);
        return !card.isEmpty();
    }

    public boolean checkByCardNumber(String cardNum){
        return cardRepository.existsByNumber(cardNum);
    }
}

