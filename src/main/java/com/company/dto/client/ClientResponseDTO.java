package com.company.dto.client;

import com.company.enums.GeneralStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientResponseDTO {

    private String id;
    private GeneralStatus status;
    private String passportNumber;
    private String passportSeries;
    private String phone;
    private String name;
    private String middleName;
    private String surname;
}