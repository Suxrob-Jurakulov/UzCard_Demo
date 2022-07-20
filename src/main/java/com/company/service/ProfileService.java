package com.company.service;

import com.company.dto.ProfileDTO;
import com.company.entity.ProfileEntity;
import com.company.enums.GeneralRole;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import com.company.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

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
}
