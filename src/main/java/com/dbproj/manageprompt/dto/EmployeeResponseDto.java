package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.EmployeeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeResponseDto {
    private final Long empId;
    private final String empName;
    private final String empSsn;
    private final String empEdu;
    private final String empEmail;
    private final Integer empWorkEx;
    private final String empSkill;

    public static EmployeeResponseDto from(EmployeeEntity employeeEntity) {
        return EmployeeResponseDto.builder()
                .empId(employeeEntity.getEmpId())
                .empName(employeeEntity.getEmpName())
                .empSsn(employeeEntity.getEmpSsn())
                .empEdu(employeeEntity.getEmpEdu())
                .empEmail(employeeEntity.getEmpEmail())
                .empWorkEx(employeeEntity.getEmpWorkEx())
                .empSkill(employeeEntity.getEmpSkill())
                .build();
    }
}
