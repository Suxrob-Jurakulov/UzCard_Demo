package com.company.repository;

import com.company.entity.CardEntity;
import com.company.enums.GeneralStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<CardEntity, String> {

    @Transactional
    @Modifying
    @Query(value = "update CardEntity set phone = ?1, status = 'ACTIVE' where id = ?2")
    void updatePhone(String phone, String id);

    @Transactional
    @Modifying
    @Query(value = "update CardEntity set status = ?1 where id = ?2")
    void changeStatus(GeneralStatus status, String id);

    List<CardEntity> findByPhone(String phone);

    List<CardEntity> findByClientId(String clientId);

    List<CardEntity> findByClientIdAndCompanyId(String clientId, String companyId);

    Optional<CardEntity> findByNumber(String number);

}
