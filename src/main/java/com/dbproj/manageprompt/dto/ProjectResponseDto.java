package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.ProjectEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.stream.Collectors;

// 프로젝트 관리
@Getter
@Builder(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectResponseDto {
    private final String proId;
    private final String proName;
    private final Date startDate;
    private final Date endDate;
    private final Integer budget;
    private final String clientName;

    public static ProjectResponseDto from(ProjectEntity projectEntity) {
        return ProjectResponseDto.builder()
                .proId(projectEntity.getProId())
                .proName(projectEntity.getProName())
                .startDate(projectEntity.getStartDate())
                .endDate(projectEntity.getEndDate())
                .budget(projectEntity.getBudget())
                .clientName(projectEntity.getClientInfoEntity().getClientName())
                .build();
    }
}
