package com.company.service;

import com.company.config.CustomUserDetails;
import com.company.dto.AuthDTO;
import com.company.dto.CurrentUserDTO;
import com.company.dto.JwtDTO;
import com.company.dto.ResponseDTO;
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

    public ResponseDTO login(AuthDTO authDTO) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword()));

        CustomUserDetails user = (CustomUserDetails) authenticate.getPrincipal();

        String username = user.getUsername();

        JwtDTO jwtDTO = new JwtDTO();
        jwtDTO.setId(user.getId());
        jwtDTO.setRole(user.getRole().name());

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setJwt(JwtUtil.encode(jwtDTO));
        responseDTO.setUsername(username);
        responseDTO.setRole(user.getRole());
        return responseDTO;
    }

    public CurrentUserDTO getCurrentUser() {
        CurrentUserDTO dto = new CurrentUserDTO();
        dto.setId(CurrentUserUtil.currentUser().getId());
        dto.setUsername(CurrentUserUtil.currentUser().getUsername());
        dto.setRole(CurrentUserUtil.currentUser().getRole());
        return dto;
    }
}

