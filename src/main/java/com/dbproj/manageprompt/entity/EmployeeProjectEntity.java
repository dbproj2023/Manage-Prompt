package com.dbproj.manageprompt.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@Table(name = "`employee_project`")
@Getter
@Setter
@Entity
public class EmployeeProjectEntity extends BaseTime {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long emp_pro_id;

  private Long emp__id;

  private Long pro_id;

  private Long role_id;

  private Date start_date;

  private Date end_date;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "emp_id")
  private EmployeeEntity employeeEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pro_id")
  private ProjectEntity projectEntity;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "emp_pro_id")
  private Set<EvaluationInnerEntity> evaluationInnerEntities;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role_id", referencedColumnName = "role_id")
  private RoleEntity roleEntity;
}
