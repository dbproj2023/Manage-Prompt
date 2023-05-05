package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.AccountEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountResponseDto {
    private final Long acc_id;
    private final String auth_id;
    private final String auth_pw;

    public static AccountResponseDto from(AccountEntity accountEntity) {
        return new AccountResponseDto(
                accountEntity.getAcc_id(),
                accountEntity.getAuth_id(),
                accountEntity.getAuth_pw()
        );
    }
}
