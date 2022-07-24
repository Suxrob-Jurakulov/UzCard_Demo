package com.company.controller;

import com.company.dto.transfer.TransferCreateDTO;
import com.company.dto.transfer.TransferResponseDTO;
import com.company.dto.transfer.TransferStatusDTO;
import com.company.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfer")
public class TransferController {
    @Autowired
    private TransferService transferService;

    @PostMapping("/public/create")
    public ResponseEntity<TransferResponseDTO> create(@RequestBody TransferCreateDTO dto){
        TransferResponseDTO response = transferService.create(dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/public/change_status")
    public ResponseEntity<String> changeStatus(@RequestBody TransferStatusDTO dto){
        String status = transferService.changeStatus(dto);
        return ResponseEntity.ok().body(status);
    }

    @PostMapping("/public/finish/{id}")
    public ResponseEntity<?> finishTransfer(@PathVariable("id") String id){
        transferService.finishTransfer(id);
        return ResponseEntity.ok("Success");
    }

}
