package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDTO {
    private String id;
    private String name;
    private String surname;
    private String middleName;
    private String phone;
    private String passportSeries;
    private String passportNumber;
    private LocalDateTime createdDate;
}
