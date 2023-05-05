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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cus_eval_id;

  @Column(name = "communication_rating", nullable = false)
  private Integer communication_rating;

  @Column(name = "communication_detail", nullable = false) // length 255
  private Integer communication_detail;

  @Column(name = "performance_rating", nullable = false)
  private Integer performance_rating;

  @Column(name = "performance_detail", nullable = false) // length 255
  private Integer performance_detail;

  @ManyToOne(fetch = FetchType.LAZY) // 발주처 mapping (FK), 단방향
  @JoinColumn(name = "client_id", referencedColumnName = "client_id")
  private ClientInfoEntity clientInfoEntity;
}
