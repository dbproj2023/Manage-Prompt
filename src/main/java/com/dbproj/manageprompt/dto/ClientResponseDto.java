package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.ClientInfoEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientResponseDto {
    private final Long clientId;
    private final String clientName;
    private final String clientEmpName;
    private final String clientEmpPh;
    private final String clientEmpEmail;

    public static ClientResponseDto from(ClientInfoEntity clientInfoEntity) {
        return new ClientResponseDto(
                clientInfoEntity.getClientId(),
                clientInfoEntity.getClientName(),
                clientInfoEntity.getClientEmpName(),
                clientInfoEntity.getClientEmpPh(),
                clientInfoEntity.getClientEmpEmail()
        );
    }
}
