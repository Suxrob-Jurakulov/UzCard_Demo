package com.company.service;

import com.company.config.CustomUserDetails;
import com.company.dto.AuthDTO;
import com.company.dto.CurrentUserDTO;
import com.company.dto.LoginDTO;
import com.company.util.CurrentUserUtil;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    public LoginDTO login(AuthDTO authDTO) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword()));
        CustomUserDetails user = (CustomUserDetails) authenticate.getPrincipal();
        String id = user.getId();
        String username = user.getUsername();

        LoginDTO dto = new LoginDTO();
        dto.setJwt(JwtUtil.encode(id));
        dto.setUsername(username);
        return dto;
    }

    public CurrentUserDTO getCurrentUser(){
        CurrentUserDTO dto = new CurrentUserDTO();
        dto.setId(CurrentUserUtil.currentUser().getId());
        dto.setUsername(CurrentUserUtil.currentUser().getUsername());
        dto.setRole(CurrentUserUtil.currentUser().getRole());
        return dto;
    }
}

