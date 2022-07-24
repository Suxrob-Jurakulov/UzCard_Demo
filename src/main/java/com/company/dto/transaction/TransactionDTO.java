package com.company.dto.transaction;

import com.company.enums.TransactionStatus;
import com.company.enums.TransactionType;
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
public class TransactionDTO {
    String id;
    String cardId;
    Long amount;
    TransactionType type;
    String transferId;
    TransactionStatus status;
    LocalDateTime createdDate;
}
