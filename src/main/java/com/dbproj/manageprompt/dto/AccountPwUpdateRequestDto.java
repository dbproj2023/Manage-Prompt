package com.dbproj.manageprompt.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountPwUpdateRequestDto {
    @NotBlank
    private String old_pw;

    @NotBlank
    private String new_pw;

    @NotBlank
    private String new_pw_re;
}
