package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.EmployeeEntity;
import com.dbproj.manageprompt.entity.ProjectEntity;
import com.dbproj.manageprompt.entity.RoleEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ProjectEmployeeUpdateRequestDto {
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

    private Long role_id;

    private EmployeeEntity employee;

    private ProjectEntity project;

    private RoleEntity role;
}
