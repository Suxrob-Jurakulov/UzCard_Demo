package com.company.repository;

import com.company.entity.TransferEntity;
import com.company.enums.TransferStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface TransferRepository extends JpaRepository<TransferEntity, String> {

    @Transactional
    @Modifying
    @Query(value = "update TransferEntity set status = ?1 where id = ?2")
    void changeStatus(TransferStatus status, String id);


    @Transactional
    @Modifying
    @Query(value = "update TransferEntity set status = ?1 where id = ?2")
    void updateStatus(TransferStatus success, String id);
}
