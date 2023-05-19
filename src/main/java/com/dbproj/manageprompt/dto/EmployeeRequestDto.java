package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.EmployeeEntity;
import lombok.*;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequestDto {
    private Long emp_id;
    private String emp_name;
    private String emp_ssn;
    private String emp_edu;
    private String emp_email;
    private Integer emp_workex;
    private String emp_skill;

    public EmployeeEntity toEntity() {
        EmployeeEntity employee = EmployeeEntity.builder()
                .empId(emp_id)
                .empName(emp_name)
                .empSsn(emp_ssn)
                .empEdu(emp_edu)
                .empEmail(emp_email)
                .empWorkEx(emp_workex)
                .empSkill(emp_skill)
                .build();
        return employee;
    }
}
