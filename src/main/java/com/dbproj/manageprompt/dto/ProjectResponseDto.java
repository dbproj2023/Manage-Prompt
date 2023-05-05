package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.ProjectEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.stream.Collectors;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectResponseDto {
    private final Long pro_id;
    private final String pro_name;
    private final Date start_date;
    private final Date end_date;
    private final Integer budget;
    private final String client_name;

    public static ProjectResponseDto from(ProjectEntity projectEntity) {
        return ProjectResponseDto.builder()
                .pro_id(projectEntity.getPro_id())
                .pro_name(projectEntity.getPro_name())
                .start_date(projectEntity.getStart_date())
                .end_date(projectEntity.getEnd_date())
                .budget(projectEntity.getBudget())
                .client_name(projectEntity.getClientInfoEntity().getClient_name())
                .build();
    }
}
