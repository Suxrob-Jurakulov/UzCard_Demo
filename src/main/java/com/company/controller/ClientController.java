package com.company.controller;

import com.company.dto.client.ClientDTO;
import com.company.dto.client.ClientResponseDTO;
import com.company.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
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


    @GetMapping("/adm/pagination")
    public ResponseEntity<?> getShortInfoByCategoryKey(@RequestBody ClientResponseDTO dto,
                                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestParam(value = "size", defaultValue = "5") int size) {

        PageImpl<ClientDTO> list = clientService.pagination(page, size, dto);
        return ResponseEntity.ok().body(list);
    }
}
