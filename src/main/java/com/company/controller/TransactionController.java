package com.company.controller;

import com.company.dto.transaction.TransactionDTO;
import com.company.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/company/get_by_cardID/{cardId}")
    public ResponseEntity<PageImpl<TransactionDTO>> getPaginationByCardId(@PathVariable("cardId") String cardId,
                                                                          @RequestParam(name = "page", defaultValue = "0") int page,
                                                                          @RequestParam(name = "size", defaultValue = "5") int size) {
        PageImpl<TransactionDTO> response = transactionService.getPageByCardId(cardId, page, size);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/company/get_by_cardID/{cardNum}")
    public ResponseEntity<PageImpl<TransactionDTO>> getPaginationByCardNum(@PathVariable("cardNum") String cardNum,
                                                                          @RequestParam(name = "page", defaultValue = "0") int page,
                                                                          @RequestParam(name = "size", defaultValue = "5") int size) {
        PageImpl<TransactionDTO> response = transactionService.getPaginationByCardNum(cardNum, page, size);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/company/get_by_companyId/{companyId}")
    public ResponseEntity<PageImpl<TransactionDTO>> getPaginationByCompanyId(@PathVariable("companyId") String companyId,
                                                                          @RequestParam(name = "page", defaultValue = "0") int page,
                                                                          @RequestParam(name = "size", defaultValue = "5") int size) {
        PageImpl<TransactionDTO> response = transactionService.getPaginationByCompanyId(companyId, page, size);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/company/get_by_cardPhone/{cardPhone}")
    public ResponseEntity<PageImpl<TransactionDTO>> getPaginationByCardPhone(@PathVariable("cardPhone") String cardPhone,
                                                                          @RequestParam(name = "page", defaultValue = "0") int page,
                                                                          @RequestParam(name = "size", defaultValue = "5") int size) {
        PageImpl<TransactionDTO> response = transactionService.getPaginationByCardPhone(cardPhone, page, size);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/company/get_credit_by_cardId/{cardId}")
    public ResponseEntity<PageImpl<TransactionDTO>> getCreditByCardId(@PathVariable("cardId") String cardId,
                                                                          @RequestParam(name = "page", defaultValue = "0") int page,
                                                                          @RequestParam(name = "size", defaultValue = "5") int size) {
        PageImpl<TransactionDTO> response = transactionService.getCreditByCardId(cardId, page, size);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/company/get_debit_by_cardId/{cardId}")
    public ResponseEntity<PageImpl<TransactionDTO>> getDebitByCardId(@PathVariable("cardId") String cardId,
                                                                          @RequestParam(name = "page", defaultValue = "0") int page,
                                                                          @RequestParam(name = "size", defaultValue = "5") int size) {
        PageImpl<TransactionDTO> response = transactionService.getDebitByCardId(cardId, page, size);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/company/get_last_month/{cardNum}")
    public ResponseEntity<List<TransactionDTO>> getLastMonthByCardNum(@PathVariable("cardNum") String cardNum){
        List<TransactionDTO> response = transactionService.getLastMonthByCardNum(cardNum);
        return ResponseEntity.ok(response);
    }
}
