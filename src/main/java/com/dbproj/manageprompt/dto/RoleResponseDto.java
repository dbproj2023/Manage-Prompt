package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.RoleEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleResponseDto {
    private final Long role_id;
    private final String role_name;

    public static RoleResponseDto from(RoleEntity roleEntity) {
        return new RoleResponseDto(
                roleEntity.getRole_id(),
                roleEntity.getRole_name()
        );
    }
}
