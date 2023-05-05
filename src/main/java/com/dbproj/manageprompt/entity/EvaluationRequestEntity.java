package com.dbproj.manageprompt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(
        name = "`evaluation_request`",
        uniqueConstraints={
                @UniqueConstraint(
                        name = "contstraint_cus_eval_id_unique",
                        columnNames = {"cus_eval_id"}
                )
        }
)
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EvaluationRequestEntity extends BaseTime {
  @Id
  @Column(name = "cus_eval_id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cusEvalId;

  @Column(name = "communication_rating", nullable = false)
  private Integer communicationRating;

  @Column(name = "communication_detail", nullable = false) // length 255
  private String communicationDetail;

  @Column(name = "performance_rating", nullable = false)
  private Integer performanceRating;

  @Column(name = "performance_detail", nullable = false) // length 255
  private String performanceDetail;

  @ManyToOne(fetch = FetchType.LAZY) // 발주처 mapping (FK), 단방향
  @JoinColumn(name = "client_id", referencedColumnName = "client_id")
  private ClientInfoEntity clientInfoEntity;
}
