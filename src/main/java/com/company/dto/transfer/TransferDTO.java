package com.company.dto.transfer;

import com.company.enums.TransferStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransferDTO {
    String id;
    String fromCardId;
    String toCardId;
    String hiddenCardNum;
    Long totalAmount;
    Long amount;
    Long serviceAmount;
    Double servicePercentage;
    LocalDateTime createdDate = LocalDateTime.now();
    TransferStatus status;
    String companyId;
    // id (uuid), from_card_id, to_card_id, total_amount(5600),amount(5500),service_amount(100),
    //     service_percentage(1%),created_date, status(SUCCESS,FAILED,CANCELED), company_id
}
