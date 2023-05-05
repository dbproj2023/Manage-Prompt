package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.AccountEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountResponseDto {
    private final Long accId;
    private final String authId;
    private final String authPw;

    public static AccountResponseDto from(AccountEntity accountEntity) {
        return new AccountResponseDto(
                accountEntity.getAccId(),
                accountEntity.getAuthId(),
                accountEntity.getAuthPw()
        );
    }
}
