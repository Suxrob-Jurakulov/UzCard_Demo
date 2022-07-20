package com.company.dto;

import com.company.enums.GeneralRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrentUserDTO {
    private String id;
    private String username;
    private GeneralRole role;
}
