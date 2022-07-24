package com.company.service;

import com.company.dto.ProfileDTO;
import com.company.dto.client.ClientDTO;
import com.company.entity.ClientEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.GeneralRole;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import com.company.repository.custome.CustomProfileRepository;
import com.company.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private CustomProfileRepository customProfileRepository;

    public ProfileEntity get(String id){
        return profileRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Profile not found");
        });
    }

    public ProfileEntity get2(String id){
       return profileRepository.findById(id).orElse(null);
    }

    public ProfileDTO create(ProfileDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByUsername(dto.getUsername());
        if (optional.isPresent()){
            throw new BadRequestException("This profile already exist");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setUsername(dto.getUsername());
        entity.setPassword(MD5Util.getMd5(dto.getPassword()));
        entity.setRole(GeneralRole.ROLE_MODERATOR);
        entity.setSurname(dto.getSurname());

        profileRepository.save(entity);
        dto.setPassword(entity.getPassword());
        dto.setId(entity.getId());
        dto.setRole(entity.getRole());

        return dto;
    }

    public ProfileDTO update(ProfileDTO dto) {
        ProfileEntity profile = get(dto.getId());
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        profile.setUsername(dto.getUsername());
        profile.setPassword(MD5Util.getMd5(dto.getPassword()));
        profileRepository.save(profile);

        dto.setId(profile.getId());
        dto.setPassword(profile.getPassword());
        return dto;
    }

    public PageImpl<ProfileDTO> getPagination(ProfileDTO dto, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "created_date");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ProfileEntity> filter = customProfileRepository.filter(dto, pageable);
        List<ProfileDTO> dtoList = new ArrayList<>();
        filter.forEach(entity -> dtoList.add(getDTO(entity)));
        return new PageImpl<>(dtoList, pageable, filter.getTotalElements());
    }

    public ProfileDTO getDTO(ProfileEntity entity){
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setUsername(entity.getUsername());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setStatus(entity.getStatus());
        dto.setRole(entity.getRole());
        return dto;
    }
}
