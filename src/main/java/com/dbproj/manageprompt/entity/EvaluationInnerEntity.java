package com.dbproj.manageprompt.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Table(name = "`evaluation_inner`")
@Getter
@Setter
@Entity
public class EvaluationInnerEntity extends BaseTime {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long eval_id;

  private Long emp_pro_id;

  private Long evaluator;

  private Integer communication_rating;

  private String communication_detail;

  private Integer performance_rating;

  private String performance_detail;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "emp_pro_id", insertable = false, updatable = false)
  private EmployeeProjectEntity employeeProjectEntity;
}
