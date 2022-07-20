package com.company.dto.card;

import com.company.dto.ClientDTO;
import com.company.dto.CompanyDTO;
import com.company.enums.GeneralStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardDTO {
    private String id;
    private String number;
    private LocalDateTime createdDate;
    private LocalDateTime expiredDate;
    private String phone;
    private Long balance;
    private String clientId;
    private ClientDTO client;
    private String companyId;
    private CompanyDTO company;
    private GeneralStatus status;
}
