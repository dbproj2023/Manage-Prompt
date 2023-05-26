package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.EmployeeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ProjectEmployeeSearchDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date period_start;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date period_end;
    private Long role;
    private String pro_id;
    private String skill_name;
    private Integer is_work;
    private Long emp_id;
    private String emp_name;
    private String emp_ssn;
    private String emp_email;
    private String emp_edu;
    private Integer emp_workex;
    private Integer com;
    private Integer per;

}
