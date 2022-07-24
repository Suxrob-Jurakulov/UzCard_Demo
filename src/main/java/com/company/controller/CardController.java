package com.company.controller;

import com.company.dto.card.CardDTO;
import com.company.dto.card.CardFilterDTO;
import com.company.dto.card.CardPhoneDTO;
import com.company.dto.card.CardStatusDTO;
import com.company.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {
    @Autowired
    private CardService cardService;

    @PostMapping("/bank/create")
    public ResponseEntity<CardDTO> create(@RequestBody CardDTO dto){
        CardDTO response = cardService.create(dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/bank/assign_phone")
    public ResponseEntity<String> assignPhone(@RequestBody CardPhoneDTO dto){
        cardService.assignPhone(dto);
        return ResponseEntity.ok("Successfully");
    }

    @PutMapping("/company/change_status")
    public ResponseEntity<String> changeStatus(@RequestBody @Valid CardStatusDTO dto){
        String response = cardService.changeStatus(dto);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/company/get_card/{id}")
    public ResponseEntity<CardDTO> getCardById(@PathVariable("id") String id){
        CardDTO dto = cardService.getCardById(id);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/company/get_cardList/{phone}")
    public ResponseEntity<List<CardDTO>> getCardListByPhone(@PathVariable("phone") String phone){
        List<CardDTO> dtoList = cardService.getCardListByPhone(phone);
        return ResponseEntity.ok().body(dtoList);
    }

    @GetMapping("/company/get_cardList/{clientId}")
    public ResponseEntity<List<CardDTO>> getCardListByClientId(@PathVariable("clientId") String clientId){
        List<CardDTO> dtoList = cardService.getCardListByClientId(clientId);
        return ResponseEntity.ok().body(dtoList);
    }

    @GetMapping("/company/get_card_by_num/{num}")
    public ResponseEntity<CardDTO> getCardByNumber(@PathVariable("num") String num){
        CardDTO dto = cardService.getCardByNumber(num);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/company/get_card_balance_byNum/{num}")
    public ResponseEntity<CardDTO> getCardListByNumber(@PathVariable("num") String num){
        CardDTO dto = cardService.getCardBalanceByNumber(num);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/adm/filter")
    public ResponseEntity<List<CardDTO>> getByFilter(@RequestBody CardFilterDTO dto){
        List<CardDTO> response = cardService.getByFilter(dto);
        return ResponseEntity.ok().body(response);
    }

}
