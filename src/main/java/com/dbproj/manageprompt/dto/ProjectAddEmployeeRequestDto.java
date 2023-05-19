package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.*;

import lombok.*;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ProjectAddEmployeeRequestDto {
    @NotBlank
    private String pro_name;

    @NotBlank
    private Long emp_id;

    @NotBlank
    private String emp_name;

    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start_date;

    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end_date;

    @NotBlank
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
