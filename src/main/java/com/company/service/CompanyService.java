package com.company.service;

import com.company.dto.CompanyDTO;
import com.company.entity.CompanyEntity;
import com.company.enums.GeneralStatus;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.CompanyRepository;
import com.company.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public CompanyEntity get(String id){
        return companyRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Company not found");
        }) ;
    }
    public CompanyEntity get2(String id){
        return companyRepository.findById(id).orElse(null);
    }

    public CompanyDTO create(CompanyDTO dto) {
        Optional<CompanyEntity> optional = companyRepository.findByUsername(dto.getUsername());
        if (optional.isPresent()){
            throw new BadRequestException("This company already exist");
        }
        CompanyEntity entity = new CompanyEntity();
        entity.setName(dto.getName());
        entity.setContact(dto.getContact());
        entity.setUsername(dto.getUsername());
        entity.setAddress(dto.getAddress());
        entity.setPassword(MD5Util.getMd5(dto.getPassword()));
        entity.setRole(dto.getRole());
        companyRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPassword(null);
        return dto;
    }

    public CompanyDTO update(CompanyDTO dto) {
        CompanyEntity company = get(dto.getId());
        company.setName(dto.getName());
        company.setPassword(MD5Util.getMd5(dto.getPassword()));
        company.setContact(dto.getContact());
        company.setAddress(dto.getAddress());
        companyRepository.save(company);

        dto.setPassword(company.getPassword());
        return dto;
    }

    public PageImpl<CompanyDTO> getListPagination(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "created_date");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CompanyEntity> all = companyRepository.findAll(pageable);
        List<CompanyDTO> list = new LinkedList<>();
        all.forEach(company -> {
            CompanyDTO dto = new CompanyDTO();
            dto.setId(company.getId());
            dto.setName(company.getName());
            dto.setAddress(company.getAddress());
            dto.setContact(company.getContact());
            dto.setUsername(company.getUsername());
            dto.setCreatedDate(company.getCreatedDate());
            list.add(dto);
        });
        return new PageImpl<>(list, pageable, all.getTotalElements());
    }

    public void delete(String id) {
        get(id);
        companyRepository.deleteCompany(GeneralStatus.BLOCK, id);
    }
}
