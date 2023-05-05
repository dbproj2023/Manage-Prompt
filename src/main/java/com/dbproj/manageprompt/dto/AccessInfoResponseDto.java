package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.AccessInfoEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AccessInfoResponseDto {
    private final Integer access_grade;
    private final String discrete;

    public static AccessInfoResponseDto from(AccessInfoEntity accessInfoEntity) {
        return new AccessInfoResponseDto(
                accessInfoEntity.getAccess_grade(),
                accessInfoEntity.getDiscrete()
        );
    }
}
