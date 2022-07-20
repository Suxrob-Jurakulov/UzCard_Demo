package com.company.repository;

import com.company.entity.ProfileEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ProfileRepository extends PagingAndSortingRepository<ProfileEntity, String> {
    Optional<ProfileEntity> findByUsername(String username);
}
