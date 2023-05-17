package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.*;

import lombok.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ProjectAddEmployeeRequestDto {
    private String pro_name;

    private Long emp_id;

    private String emp_name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start_date;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end_date;

    private Long role_id;

    private EmployeeEntity employee;

    private ProjectEntity project;

    private RoleEntity role;

    public EmployeeProjectEntity toEntity() {
        EmployeeProjectEntity employeeProject = EmployeeProjectEntity.builder()
                .startDate(start_date)
                .endDate(end_date)
                .employeeEntity(employee)
                .projectEntity(project)
                .roleEntity(role)
                .build();

        return employeeProject;
    }
}
