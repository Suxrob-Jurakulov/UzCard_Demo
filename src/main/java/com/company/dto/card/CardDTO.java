package com.company.dto.card;

import com.company.dto.client.ClientDTO;
import com.company.dto.CompanyDTO;
import com.company.enums.GeneralStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardDTO {
    String id;
    String number;
    LocalDateTime createdDate;
    LocalDateTime expiredDate;
    String phone;
    Long balance;
    String clientId;
    ClientDTO client;
    String companyId;
    CompanyDTO company;
    GeneralStatus status;
}
