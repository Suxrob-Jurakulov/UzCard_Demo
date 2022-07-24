package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfitDTO {
    Double servicePercentage;
    Double serviceAmount;
    Double totalAmount;
    String companyCardId;
    String uzCardId;

}
