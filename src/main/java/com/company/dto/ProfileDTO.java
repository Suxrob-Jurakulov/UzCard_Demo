package com.company.dto;

import com.company.enums.GeneralRole;
import com.company.enums.GeneralStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private String id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private GeneralRole role;
    private GeneralStatus status;
    private LocalDateTime createdDate;
}
