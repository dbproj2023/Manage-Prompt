package com.dbproj.manageprompt.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmployeeUpdateRequestDto {
    @NotBlank
    private String emp_ph;

    @NotBlank
    private String emp_email;
}
