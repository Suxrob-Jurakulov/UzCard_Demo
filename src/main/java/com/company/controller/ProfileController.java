package com.company.controller;

import com.company.dto.ProfileDTO;
import com.company.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile/adm")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/create")
    public ResponseEntity<ProfileDTO> create(@RequestBody ProfileDTO dto){
        ProfileDTO response = profileService.create(dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<ProfileDTO> update(@RequestBody ProfileDTO dto){
        ProfileDTO response = profileService.update(dto);
        return ResponseEntity.ok().body(response);
    }

    // TODO filter profile with pagination
}
