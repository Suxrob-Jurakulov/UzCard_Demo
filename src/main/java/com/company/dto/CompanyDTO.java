package com.company.dto;

import com.company.enums.GeneralRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyDTO {
    private String id;
    private String name;
    private String address;
    private String contact;
    private String username;
    private String password;
    private GeneralRole role;
    private LocalDateTime createdDate;

}
