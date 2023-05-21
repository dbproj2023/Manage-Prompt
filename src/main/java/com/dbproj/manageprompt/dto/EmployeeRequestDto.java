package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.AccountEntity;
import com.dbproj.manageprompt.entity.EmployeeEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

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
    private LocalDateTime created_at;
    private String emp_ph;

    public EmployeeEntity toEntity() {
        EmployeeEntity employee = EmployeeEntity.builder()
                .empId(emp_id)
                .empName(emp_name)
                .empSsn(emp_ssn)
                .empEdu(emp_edu)
                .empEmail(emp_email)
                .empWorkEx(emp_workex)
                .empSkill(emp_skill)
                .empPh(emp_ph)
                .createdAt(created_at)
                .build();
        return employee;
    }
    public static EmployeeRequestDto toDto(EmployeeEntity employeeEntity) {
        EmployeeRequestDto dto = new EmployeeRequestDto();
        dto.setEmp_id(employeeEntity.getEmpId());
        dto.setEmp_name(employeeEntity.getEmpName());
        dto.setEmp_ssn(employeeEntity.getEmpSsn());
        dto.setEmp_email(employeeEntity.getEmpEmail());
        dto.setEmp_workex(employeeEntity.getEmpWorkEx());
        dto.setEmp_edu(employeeEntity.getEmpEdu());
        dto.setEmp_skill(employeeEntity.getEmpSkill());
        dto.setCreated_at(employeeEntity.getCreatedAt());
        dto.setEmp_ph(employeeEntity.getEmpPh());
        return dto;
    }
}
