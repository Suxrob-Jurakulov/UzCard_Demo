package com.company.controller;

import com.company.dto.AuthDTO;
import com.company.dto.LoginDTO;
import com.company.service.AuthService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//
//
//@Slf4j
//@Api(tags = "Authorization and Registration")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
//
//    @PostMapping("/registration")
//    public ResponseEntity<String> registration(@RequestBody RegistrationDTO dto){
//        String response = authService.registration(dto);
//        return ResponseEntity.ok().body(response);
//    }

    @PostMapping("/public/login")
    public ResponseEntity<LoginDTO> login(@RequestBody AuthDTO dto) {
        LoginDTO response = authService.login(dto);
        return ResponseEntity.ok().body(response);
    }

}

