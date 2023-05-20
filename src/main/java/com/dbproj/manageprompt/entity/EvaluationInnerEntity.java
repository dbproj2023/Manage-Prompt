package com.dbproj.manageprompt.entity;

import lombok.*;

import javax.persistence.*;

@Builder
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
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EvaluationInnerEntity extends BaseTime {
  @Id
  @Column(name = "eval_id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long evalId;

  @Column(name = "evaluator", nullable = false) // 평가자 사번
  private Long evaluator; // 평가자

  @Column(name = "communication_rating", nullable = false)
  private Integer communicationRating;

  @Column(name = "communication_detail", nullable = true) // length 255
  private String communicationDetail;

  @Column(name = "performance_rating", nullable = false)
  private Integer performanceRating;

  @Column(name = "performance_detail", nullable = true) // length 255
  private String performanceDetail;

  @ManyToOne(fetch = FetchType.LAZY) // 발주처 mapping (FK), 양방향
  @JoinColumn(name = "emp_pro_id", referencedColumnName = "emp_pro_id")
  private EmployeeProjectEntity employeeProjectEntity;
}
