package com.company.repository.custome;

import com.company.dto.ProfileDTO;
import com.company.entity.ProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CustomProfileRepository {
    @PersistenceContext
    private EntityManager entityManager;


    public Page<ProfileEntity> filter(ProfileDTO dto, Pageable pageable) {

        StringBuilder build = new StringBuilder();
        build.append(" SELECT a FROM ProfileEntity a ");
        build.append(" where visible = true  ");

        String addition = "";
        if (dto.getId() != null) {
            addition += (" and a.id = '" + dto.getId() + "' ");
        }

        if (dto.getStatus() != null) {
            addition += (" and a.status = '" + dto.getStatus() + "' ");
        }

        if (dto.getName() != null) {
            addition += (" and a.passportNumber = '" + dto.getName() + "' ");
        }

        if (dto.getSurname() != null) {
            addition += (" and a.passportSeries='" + dto.getSurname() + "' ");
        }

        if (dto.getUsername() != null) {
            addition += (" and a.phone='" + dto.getUsername() + "'");
        }

        if (dto.getRole() != null) {
            addition += (" and a.middleName='" + dto.getRole() + "'");
        }
        build.append(addition);


        Query query = entityManager.createQuery(build.toString());

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        query.setFirstResult((pageNumber) * pageSize);
        query.setMaxResults(pageSize);
        List <ProfileEntity> fooList = query.getResultList();

        String builder1 = " SELECT count(a) FROM ProfileEntity a " +
                " where a.visible = true  " +
                addition;
        Query queryTotal = entityManager.createQuery(builder1);

        long countResult = (long)queryTotal.getSingleResult();
        int i=(int)countResult;
        return new PageImpl<>(fooList, pageable, i);
    }

}
