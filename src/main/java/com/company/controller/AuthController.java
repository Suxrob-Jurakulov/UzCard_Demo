package com.company.controller;

import com.company.dto.AuthDTO;
import com.company.dto.ResponseDTO;
import com.company.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResponseDTO> login(@RequestBody AuthDTO dto) {
        ResponseDTO response = authService.login(dto);
        return ResponseEntity.ok().body(response);
    }

}

