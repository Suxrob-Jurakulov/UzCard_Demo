package com.company.repository;

import com.company.entity.ClientEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ClientRepository extends PagingAndSortingRepository<ClientEntity, String> {

    Optional<ClientEntity> findByPassportSeriesAndPassportNumber(String passportSeries, String passportNumber);
}
