//package com.company.repository.custome;
//
//import com.company.dto.client.ClientDTO;
//import com.company.entity.ClientEntity;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.Query;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.List;
//
//@Repository
//public class CustomClientRepository {
//    @Autowired
//    private EntityManager entityManager;
//
//
//    public List<ClientEntity> filter(ClientDTO dto) {
//
//        StringBuilder builder = new StringBuilder();
//        builder.append(" SELECT a FROM ClientEntity a ");
//        builder.append(" where visible = true ");
//
//        if (dto.getId() != null) {
//            builder.append(" and a.id = '" + dto.getId() + "' ");
//        }
//
//        if (dto.getTitle() != null) {
//            builder.append(" and a.title = '" + dto.getTitle() + "' ");
//        }
//        // Select a from ClientEntity a where title = 'asdasd'; delete from sms-- fdsdfsdfs'
//        if (dto.getStatus() != null) {
//            builder.append(" and a.status = '" + dto.getStatus() + "' ");
//        }
//
//        if (dto.getDescription() != null) {
//            builder.append(" and a.description like '%" + dto.getStatus() + "%' ");
//        }
//
//        if (dto.getCategoryId() != null) {
//            builder.append(" and a.category.id=" + dto.getCategoryId() + " ");
//        }
//
//        if (dto.getRegionId() != null) {
//            builder.append(" and a.region.id=" + dto.getRegionId() + " ");
//        }
//
//        if (dto.getRegionId() != null) {
//            builder.append(" and a.region.id=" + dto.getRegionId() + " ");
//        }
//
//        if (dto.getPublishedDateFrom() != null && dto.getPublishedDateTo() == null) {
//            // builder.append(" and a.publishDate = '" + dto.getPublishedDateFrom() + "' ");
//            LocalDate localDate = LocalDate.parse(dto.getPublishedDateFrom());
//            LocalDateTime fromTime = LocalDateTime.of(localDate, LocalTime.MIN); // yyyy-MM-dd 00:00:00
//            LocalDateTime toTime = LocalDateTime.of(localDate, LocalTime.MAX); // yyyy-MM-dd 23:59:59
//            builder.append(" and a.publishDate between '" + fromTime + "' and '" + toTime + "' ");
//        } else if (dto.getPublishedDateFrom() != null && dto.getPublishedDateTo() != null) {
//            builder.append(" and a.publishDate between '" + dto.getPublishedDateFrom() + "' and '" + dto.getPublishedDateTo() + "' ");
//        }
//
//        Query query = entityManager.createQuery(builder.toString());
//        List<ClientEntity> articleList = query.getResultList();
//
//        return articleList;
//    }
//
//}

package com.company.repository.custome;

import com.company.dto.client.ClientResponseDTO;
import com.company.entity.ClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CustomClientRepository {
    @PersistenceContext
    private EntityManager entityManager;


    public Page<ClientEntity> filter(ClientResponseDTO dto, Pageable pageable) {

        StringBuilder builder = new StringBuilder();
        builder.append(" SELECT a FROM ClientEntity a ");
        builder.append(" where visible = true  ");

        String addition = "";
        if (dto.getId() != null) {
            addition += (" and a.id = '" + dto.getId() + "' ");
        }

        if (dto.getStatus() != null) {
            addition += (" and a.status = '" + dto.getStatus() + "' ");
        }

        if (dto.getPassportNumber() != null) {
            addition += (" and a.passportNumber = '" + dto.getPassportNumber() + "' ");
        }

        if (dto.getPassportSeries() != null) {
            addition += (" and a.passportSeria='" + dto.getPassportSeries() + "' ");
        }

        if (dto.getPhone() != null) {
            addition += (" and a.phone='" + dto.getPhone() + "'");
        }
        if (dto.getName() != null) {
            addition += (" and a.name='" + dto.getName() + "'");
        }
        if (dto.getSurname() != null) {
            addition += (" and a.surname='" + dto.getSurname() + "'");
        }
        if (dto.getMiddleName() != null) {
            addition += (" and a.middleName='" + dto.getMiddleName() + "'");
        }
        builder.append(addition);


        Query query = entityManager.createQuery(builder.toString());
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        query.setFirstResult((pageNumber) * pageSize);
        query.setMaxResults(pageSize);
        List <ClientEntity> fooList = query.getResultList();

        String builder1 = " SELECT count(a) FROM ClientEntity a " +
                " where a.visible = true  " +
                addition;
        Query queryTotal = entityManager.createQuery
                (builder1);
        long countResult = (long)queryTotal.getSingleResult();
        int i=(int)countResult;
        return new PageImpl<>(fooList, pageable, i);
    }
}