package com.dbproj.manageprompt.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientUpdateRequestDto {
    @NotBlank
    private String clientEmpName;

    @NotBlank
    private String clientEmpPh;

    @NotBlank
    private String clientEmpEmail;
}
