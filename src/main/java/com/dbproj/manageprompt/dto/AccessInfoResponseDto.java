package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.AccessInfoEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AccessInfoResponseDto {
    private final Integer accessGrade;
    private final String discrete;

    public static AccessInfoResponseDto from(AccessInfoEntity accessInfoEntity) {
        return new AccessInfoResponseDto(
                accessInfoEntity.getAccessGrade(),
                accessInfoEntity.getDiscrete()
        );
    }
}
