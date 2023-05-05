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
    private final Long emp_id;
    private final String emp_name;
    private final String emp_ssn;
    private final String emp_edu;
    private final String emp_email;
    private final Integer emp_work_ex;
    private final String emp_skill;

    public static EmployeeResponseDto from(EmployeeEntity employeeEntity) {
        return EmployeeResponseDto.builder()
                .emp_id(employeeEntity.getEmp_id())
                .emp_name(employeeEntity.getEmp_name())
                .emp_ssn(employeeEntity.getEmp_ssn())
                .emp_edu(employeeEntity.getEmp_edu())
                .emp_email(employeeEntity.getEmp_email())
                .emp_work_ex(employeeEntity.getEmp_work_ex())
                .emp_skill(employeeEntity.getEmp_skill())
                .build();
    }
}
