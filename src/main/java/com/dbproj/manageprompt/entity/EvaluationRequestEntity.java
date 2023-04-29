package com.dbproj.manageprompt.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Table(name = "`evaluation_request`")
@Getter
@Setter
@Entity
public class EvaluationRequestEntity extends BaseTime {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cus_eval_id;

  private Long client_id;

  private Integer communication_rating;

  private Integer communication_detail;

  private Integer performance_rating;

  private Integer performance_detail;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "client_id")
  private ClientInfoEntity clientInfoEntity;
}
