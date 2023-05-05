package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.ProjectEntity;

import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProjectRequestDto {
    private String proName;
    private Date startDate;
    private Date endDate;
    private Integer budget;

    public ProjectEntity toEntity() {
        ProjectEntity project = ProjectEntity.builder()
                .proName(proName)
                .startDate(startDate)
                .endDate(endDate)
                .budget(budget)
                .build();

        return project;
    }
}
