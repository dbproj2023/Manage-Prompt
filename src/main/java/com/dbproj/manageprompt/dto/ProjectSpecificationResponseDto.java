package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.ProjectEntity;
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
public class ProjectSpecificationResponseDto {
    private final Integer year;
    private final Date startDate;
    private final Date endDate;
    private final String proName;
    private final Integer budget;
    private final Integer numOfParicipant;
    private final String state;
    private final String clientName;
    private final List<EmployeeProjectResponseDto> participantList; // PM 찾는 용도

    public static ProjectSpecificationResponseDto from(ProjectEntity projectEntity) {
        return ProjectSpecificationResponseDto.builder()
//                .year()
                .startDate(projectEntity.getStartDate())
                .endDate(projectEntity.getEndDate())
                .proName(projectEntity.getProName())
                .budget(projectEntity.getBudget())
                .numOfParicipant(projectEntity.getEmployeeProjectEntities().size())
//                .state()
                .clientName(projectEntity.getClientInfoEntity().getClientName())
                // PM 찾는 용도
                .participantList(projectEntity.getEmployeeProjectEntities().stream().map(EmployeeProjectResponseDto::from).collect(Collectors.toList()))
                .build();
    }
}
