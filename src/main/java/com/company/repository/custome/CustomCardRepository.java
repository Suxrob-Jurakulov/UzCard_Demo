package com.company.repository.custome;

import com.company.dto.card.CardDTO;
import com.company.dto.card.CardFilterDTO;
import com.company.entity.CardEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CustomCardRepository {
    @PersistenceContext
    private EntityManager entityManager;


    public List<CardEntity> filter(CardFilterDTO dto) {

        StringBuilder build = new StringBuilder();
        build.append(" SELECT a FROM CardEntity a ");
        build.append(" where visible = true  ");

        String addition = "";

        if (dto.getStatus() != null) {
            addition += (" and a.status = '" + dto.getStatus() + "' ");
        }
        if (dto.getClientStatus() != null) {
            addition += (" and a.client.status = '" + dto.getClientStatus() + "' ");
        }

        if (dto.getNumber() != null) {
            addition += (" and a.number = '" + dto.getNumber() + "' ");
        }

        if (dto.getPhone() != null) {
            addition += (" and a.phone='" + dto.getPhone() + "'");
        }

        if (dto.getClientId() != null) {
            addition += (" and a.clientId='" + dto.getClientId() + "'");
        }
        if (dto.getClientName() != null) {
            addition += (" and a.client.name='" + dto.getClientName() + "'");
        }
        if (dto.getBankName() != null) {
            addition += (" and a.company.name='" + dto.getBankName() + "'");
        }
        if (dto.getBalance() != null) {
            addition += (" and a.balance > '" + dto.getBalance() + "'");
        }
        build.append(addition);


        Query query = entityManager.createQuery(build.toString());
        return query.getResultList();
    }

}
