package com.company.controller;

import com.company.dto.CompanyDTO;
import com.company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @PostMapping("/adm/create")
    public ResponseEntity<CompanyDTO> create(@RequestBody CompanyDTO dto) {
        CompanyDTO response = companyService.create(dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/adm/update")
    public ResponseEntity<CompanyDTO> update(@RequestBody CompanyDTO dto) {
        CompanyDTO response = companyService.update(dto);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/adm/get")
    public ResponseEntity<PageImpl<CompanyDTO>> getPagination(@RequestParam(name = "page", defaultValue = "0") int page,
                                                              @RequestParam(name = "size", defaultValue = "2") int size){
        PageImpl<CompanyDTO> response = companyService.getListPagination(page, size);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id){
        companyService.delete(id);
        return ResponseEntity.ok("Deleted");
    }
}
