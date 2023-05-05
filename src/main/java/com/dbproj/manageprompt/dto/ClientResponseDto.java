package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.ClientInfoEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientResponseDto {
    private final Long client_id;
    private final String client_name;
    private final String client_emp_name;
    private final String client_emp_ph;
    private final String client_emp_email;

    public static ClientResponseDto from(ClientInfoEntity clientInfoEntity) {
        return new ClientResponseDto(
                clientInfoEntity.getClient_id(),
                clientInfoEntity.getClient_name(),
                clientInfoEntity.getClient_emp_name(),
                clientInfoEntity.getClient_emp_ph(),
                clientInfoEntity.getClient_emp_email()
        );
    }
}
