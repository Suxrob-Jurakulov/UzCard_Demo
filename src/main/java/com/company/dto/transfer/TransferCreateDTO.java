package com.company.dto.transfer;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransferCreateDTO {
    private String fromCard;
    private String toCard;
    private Long amount;
    private String companyId;


}
