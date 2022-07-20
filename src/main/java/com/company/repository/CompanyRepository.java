package com.company.repository;

import com.company.entity.CompanyEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.GeneralStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface CompanyRepository extends PagingAndSortingRepository<CompanyEntity, String> {
    Optional<CompanyEntity> findByUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "update CompanyEntity set status = ?1 where id = ?2")
    void deleteCompany(GeneralStatus status, String id);
}
