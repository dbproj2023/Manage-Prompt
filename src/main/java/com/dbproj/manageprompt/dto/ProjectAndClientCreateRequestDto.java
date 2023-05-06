package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.ClientInfoEntity;
import com.dbproj.manageprompt.entity.ProjectEntity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ProjectAndClientCreateRequestDto {
    private String pro_id;
    private String pro_name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start_date;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end_date;
    private Integer budget;

    private String client_name;

    private String client_emp_name;

    private String client_emp_ph;

    private String client_emp_email;
    private ClientInfoEntity client;

    public ProjectEntity toEntity() {
        ProjectEntity project = ProjectEntity.builder()
                .proId(pro_id)
                .proName(pro_name)
                .startDate(start_date)
                .endDate(end_date)
                .budget(budget)
                .clientInfoEntity(client)
                .build();

        return project;
    }
}
