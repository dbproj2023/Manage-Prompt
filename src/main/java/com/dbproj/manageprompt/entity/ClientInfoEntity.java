package com.dbproj.manageprompt.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Table(name = "`client_info`")
@Getter
@Setter
@Entity
public class ClientInfoEntity extends BaseTime {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long client_id;

  private String client_name;

  private String client_emp_name;

  private String client_emp_ph;

  private String client_emp_email;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "client_id")
  private Set<EvaluationRequestEntity> evaluationRequestEntities;
}
