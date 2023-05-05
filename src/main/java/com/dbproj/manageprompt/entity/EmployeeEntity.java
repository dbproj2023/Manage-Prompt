package com.dbproj.manageprompt.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

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
}
