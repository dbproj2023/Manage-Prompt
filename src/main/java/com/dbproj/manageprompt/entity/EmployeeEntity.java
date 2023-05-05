package com.dbproj.manageprompt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmployeeEntity extends BaseTime {
  @Id @Column(name = "emp_id")
  private Long emp_id;

  @Column(name = "emp_name", nullable = false, length = 20)
  private String emp_name;

  @Column(name = "emp_ssn", nullable = false, length = 20)
  private String emp_ssn;

  @Column(name = "emp_edu", nullable = false, length = 10)
  private String emp_edu;

  @Column(name = "emp_email", nullable = false) // length 255
  private String emp_email;

  @Column(name = "emp_work_ex", nullable = false)
  private Integer emp_work_ex;

  @Column(name = "emp_skill", nullable = false) // length 255
  private String emp_skill;
}
