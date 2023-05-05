package com.dbproj.manageprompt.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EmailAuthDto {

    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    private String code;
}
