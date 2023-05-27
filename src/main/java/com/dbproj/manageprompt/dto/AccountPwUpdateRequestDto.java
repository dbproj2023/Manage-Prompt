package com.dbproj.manageprompt.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountPwUpdateRequestDto {
//    @NotBlank
    private String old_pw;

//    @NotBlank
    private String new_pw;

//    @NotBlank
    private String new_pw_re;
}
