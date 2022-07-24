package com.company.dto.transfer;

import com.company.enums.TransferStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferStatusDTO {
    private String id;
    private TransferStatus status;
}
