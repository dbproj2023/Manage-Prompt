package com.dbproj.manageprompt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(
        name = "`evaluation_inner`",
        uniqueConstraints={
                @UniqueConstraint(
                        name = "contstraint_eval_id_unique",
                        columnNames = {"eval_id"}
                )
        }
)
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EvaluationInnerEntity extends BaseTime {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long eval_id;

  @Column(name = "evaluator", nullable = false) // 평가자 사번
  private Long evaluator;

  @Column(name = "communication_rating", nullable = false)
  private Integer communication_rating;

  @Column(name = "communication_detail", nullable = false) // length 255
  private String communication_detail;

  @Column(name = "performance_rating", nullable = false)
  private Integer performance_rating;

  @Column(name = "performance_detail", nullable = false) // length 255
  private String performance_detail;

  @ManyToOne(fetch = FetchType.LAZY) // 발주처 mapping (FK), 양방향
  @JoinColumn(name = "emp_pro_id", referencedColumnName = "emp_pro_id")
  private EmployeeProjectEntity employeeProjectEntity;
}
