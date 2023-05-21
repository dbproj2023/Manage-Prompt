package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.EmployeeEntity;

import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectEmployeeResponseDto {

    private List<EmployeeProjectNotIncludeEmployeeResponseDto> projectList;

    public static ProjectEmployeeResponseDto from(EmployeeEntity employeeEntity) {
        return ProjectEmployeeResponseDto.builder()
                .projectList(employeeEntity.getEmployeeProjectEntities().stream().map(EmployeeProjectNotIncludeEmployeeResponseDto::from).collect(Collectors.toList()))
                .build();
    }
}