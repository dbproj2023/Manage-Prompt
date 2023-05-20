package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.AccountEntity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class AccountRequestDto {
    private Long accId;
    private String authId;
    private String authPw;
    private Integer accessGrade;

    public static AccountRequestDto toDto(AccountEntity accountEntity) {
        AccountRequestDto accountRequestDto = new AccountRequestDto();
        accountRequestDto.setAccId(accountEntity.getAccId());
        accountRequestDto.setAuthId(accountEntity.getAuthId());
        accountRequestDto.setAuthPw(accountEntity.getAuthPw());
        accountRequestDto.setAccessGrade(accountEntity.getAccessInfoEntity().getAccessGrade());
        return accountRequestDto;
    }
}
