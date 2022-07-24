package com.company.dto;

import com.company.enums.GeneralRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {
    private String username;
    private GeneralRole role;
    private String jwt;

}
