package com.company.repository;

import com.company.entity.CardEntity;
import com.company.enums.GeneralRole;
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

    @Transactional
    @Modifying
    @Query(value = "update CardEntity set balance = balance - ?2 where id = ?1")
    void fromCardCredit(String fromCardId, Long totalAmount);

    @Transactional
    @Modifying
    @Query(value = "update CardEntity set balance = balance + ?2 where id = ?1")
    void toCardDebit(String toCardId, Long amount);

    @Transactional
    @Modifying
    @Query(value = "update CardEntity set balance = balance + ?1 where companyId = ?2")
    void debitCompanyCard(Long companyAmount, String companyId);

    @Transactional
    @Modifying
    @Query(value = "update CardEntity c set c.balance = c.balance + ?1 where c.id = ?2 ")
    void debitToUzCard(Long uzCardAmount, String cardId);

    @Query(value = "from CardEntity c where c.company.role = ?1")
    CardEntity findByCompanyRole(GeneralRole company_role);

    boolean existsByNumber(String number);
}
