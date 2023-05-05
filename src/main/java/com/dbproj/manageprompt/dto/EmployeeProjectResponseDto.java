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
    private final Long emp_pro_id;
    private final Date start_date;
    private final Date end_date;

    public static EmployeeProjectResponseDto from(EmployeeProjectEntity employeeProjectEntity) {
        return EmployeeProjectResponseDto.builder()
                .emp_pro_id(employeeProjectEntity.getEmp_pro_id())
                .start_date(employeeProjectEntity.getStart_date())
                .end_date(employeeProjectEntity.getEnd_date())
                .build();
    }
}
