package com.dbproj.manageprompt.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccessUpdateRequestDto {
    @NotBlank
    private Long emp_id;
    @NotBlank
    private String emp_name;
    @NotBlank
    private Integer access_grade;
}
