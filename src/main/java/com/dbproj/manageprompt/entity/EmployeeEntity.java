package com.dbproj.manageprompt.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@Table(name = "`employee`")
@Getter
@Setter
@Entity
public class EmployeeEntity extends BaseTime {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long emp_id;

  private String emp_name;

  private String emp_ssn;

  private String emp_edu;

  private String emp_email;

  private Integer emp_work_ex;

  private String emp_skill;
}
