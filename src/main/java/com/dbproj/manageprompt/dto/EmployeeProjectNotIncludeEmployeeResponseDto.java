package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.EmployeeProjectEntity;

import lombok.AccessLevel;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeProjectNotIncludeEmployeeResponseDto {

    private final String proId;
    private final String proName;
    private final String roleName;
    private final Date startDate;
    private final Date endDate;

    public static EmployeeProjectNotIncludeEmployeeResponseDto from(EmployeeProjectEntity employeeProjectEntity) {
        return EmployeeProjectNotIncludeEmployeeResponseDto.builder()
                .proId(employeeProjectEntity.getProjectEntity().getProId())
                .proName(employeeProjectEntity.getProjectEntity().getProName())
                .roleName(employeeProjectEntity.getRoleEntity().getRoleName())
                .startDate(employeeProjectEntity.getStartDate())
                .endDate(employeeProjectEntity.getEndDate())
                .build();
    }
}