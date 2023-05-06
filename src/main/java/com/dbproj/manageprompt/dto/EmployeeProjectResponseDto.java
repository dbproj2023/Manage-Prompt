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
public class EmployeeProjectResponseDto {
    private final Long empProId;
    private final EmployeeResponseDto employee;
    private final Date startDate;
    private final Date endDate;
    private final RoleResponseDto roleId;
    private final String proId;
    private final String proName;

    public static EmployeeProjectResponseDto from(EmployeeProjectEntity employeeProjectEntity) {
        return EmployeeProjectResponseDto.builder()
                .empProId(employeeProjectEntity.getEmpProId())
                .employee(EmployeeResponseDto.from(employeeProjectEntity.getEmployeeEntity()))
                .startDate(employeeProjectEntity.getStartDate())
                .endDate(employeeProjectEntity.getEndDate())
                .roleId(RoleResponseDto.from(employeeProjectEntity.getRoleEntity()))
                .proId(employeeProjectEntity.getProjectEntity().getProId())
                .proName(employeeProjectEntity.getProjectEntity().getProName())
                .build();
    }
}