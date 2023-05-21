package com.dbproj.manageprompt.entity;

import com.dbproj.manageprompt.dto.AccountCreateRequestDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Table(
        name = "`employee`",
        uniqueConstraints={
                @UniqueConstraint(
                        name = "contstraint_emp_id_unique",
                        columnNames = {"emp_id"}
                )
        }
)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmployeeEntity extends BaseTime {
  @Id @Column(name = "emp_id")
  private Long empId;

  @Column(name = "emp_name", nullable = false, length = 20)
  private String empName;

  @Column(name = "emp_ssn", nullable = false, length = 20)
  private String empSsn;

  @Column(name = "emp_edu", nullable = false, length = 10)
  private String empEdu;

  @Column(name = "emp_email", nullable = false) // length 255
  private String empEmail;

  @Column(name = "emp_work_ex", nullable = false)
  private Integer empWorkEx;

  @Column(name = "emp_skill", nullable = false) // length 255
  private String empSkill;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @OneToMany(mappedBy = "employeeEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL) // 직원_프로젝트 mapping (FK), 양방향
  private List<EmployeeProjectEntity> employeeProjectEntities;

  @Column(name = "emp_ph")
  private String empPh;

  public static EmployeeEntity toUpdateEmployeeEntity(AccountCreateRequestDto memberDTO) {
    EmployeeEntity employeeEntity = new EmployeeEntity();
    employeeEntity.setEmpId(memberDTO.getEmp_id());
    employeeEntity.setEmpSsn(memberDTO.getEmp_ssn());
    employeeEntity.setEmpName(memberDTO.getEmp_name());
    employeeEntity.setEmpEmail(memberDTO.getEmp_email());
    employeeEntity.setEmpWorkEx(memberDTO.getEmp_work_ex());
    employeeEntity.setEmpSkill(memberDTO.getEmp_skill());
    employeeEntity.setEmpEdu(memberDTO.getEmp_edu());
    employeeEntity.setEmpPh(memberDTO.getEmp_ph());
    return employeeEntity;
  }

  public void update(String emdPh, String empEmail) {
    if (emdPh != null) {
      this.empPh = emdPh;
    }
    if (empEmail != null) {
      this.empEmail = empEmail;
    }
  }
}

