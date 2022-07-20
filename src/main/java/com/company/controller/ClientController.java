package com.company.controller;

import com.company.dto.ClientDTO;
import com.company.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping("/bank/create")
    public ResponseEntity<ClientDTO> create(@RequestBody ClientDTO dto){
        ClientDTO response = clientService.create(dto);
        return ResponseEntity.ok().body(response);
    }
    @PutMapping ("/bank/update")
    public ResponseEntity<ClientDTO> update(@RequestBody ClientDTO dto){
        ClientDTO response = clientService.update(dto);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/adm/get_by_id/{id}")
    public ResponseEntity<ClientDTO> getByIdAdmin(@PathVariable("id") String clientId){
        ClientDTO dto = clientService.getById(clientId);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/bank/get_by_id/{id}")
    public ResponseEntity<ClientDTO> getByIdBank(@PathVariable("id") String clientId){
        ClientDTO dto = clientService.getByIdBank(clientId);
        return ResponseEntity.ok().body(dto);
    }
}
