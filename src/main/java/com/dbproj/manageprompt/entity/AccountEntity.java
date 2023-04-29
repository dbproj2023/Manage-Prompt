package com.dbproj.manageprompt.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Table(name = "`account`")
@Getter
@Setter
@Entity
public class AccountEntity extends BaseTime {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long emp_id;

  private Integer access_grade;

  private String auth_id;

  private String auth_pw;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
  private EmployeeEntity employeeEntity;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "access_grade_id", referencedColumnName = "access_grade")
  private AccessInfoEntity accessInfoEntity;
}
