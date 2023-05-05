package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.ClientInfoEntity;
import com.dbproj.manageprompt.entity.ProjectEntity;

import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ProjectAndClientCreateRequestDto {
    private String proName;
    private Date startDate;
    private Date endDate;
    private Integer budget;

    private String clientName;

    private String clientEmpName;

    private String clientEmpPh;

    private String clientEmpEmail;
    private ClientInfoEntity client;

    public ProjectEntity toEntity() {
        ProjectEntity project = ProjectEntity.builder()
                .proName(proName)
                .startDate(startDate)
                .endDate(endDate)
                .budget(budget)
                .clientInfoEntity(client)
                .build();

        return project;
    }
}
