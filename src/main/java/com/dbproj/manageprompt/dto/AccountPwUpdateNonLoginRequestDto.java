package com.dbproj.manageprompt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountPwUpdateNonLoginRequestDto {
    private String id;

    private String new_pw;

    private String new_pw_re;
}
