package com.dbproj.manageprompt.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientUpdateRequestDto {
    @NotBlank
    private String client_emp_name;

    @NotBlank
    private String client_emp_ph;

    @NotBlank
    private String client_emp_email;
}
