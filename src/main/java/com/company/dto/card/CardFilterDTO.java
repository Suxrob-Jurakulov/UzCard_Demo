package com.company.dto.card;

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
public class CardFilterDTO {

    String phone;
    String number;
    LocalDateTime createdDate;
    LocalDateTime expiredDate;
    Long balance;
    String bankName;
    GeneralStatus status;
    String clientId;
    String clientName;
    GeneralStatus clientStatus;
}
