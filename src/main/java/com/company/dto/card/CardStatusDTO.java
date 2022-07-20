package com.company.dto.card;

import com.company.enums.GeneralStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardStatusDTO {
    @NotNull
    private String id;
    @NotNull
    private GeneralStatus status;
}
