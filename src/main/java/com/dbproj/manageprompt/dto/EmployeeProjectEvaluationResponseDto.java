package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.EmployeeProjectEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeProjectEvaluationResponseDto {
    private final Long empProId;
    private final Date startDate;
    private final Date endDate;
    private final String roleName;
    private final String proName;
    private final List<EvaluationInnerResponseDto> evaluationList;

    public static EmployeeProjectEvaluationResponseDto from(EmployeeProjectEntity employeeProjectEntity) {
        return EmployeeProjectEvaluationResponseDto.builder()
                .empProId(employeeProjectEntity.getEmpProId())
                .startDate(employeeProjectEntity.getStartDate())
                .endDate(employeeProjectEntity.getEndDate())
//                .roleName(RoleResponseDto.from(employeeProjectEntity.getRoleEntity()))
                .roleName(employeeProjectEntity.getRoleEntity().getRoleName())
                .proName(employeeProjectEntity.getProjectEntity().getProName())
                .evaluationList(employeeProjectEntity.getEvaluationInnerEntities().stream().map(EvaluationInnerResponseDto::from).collect(Collectors.toList()))
                .build();
    }
}