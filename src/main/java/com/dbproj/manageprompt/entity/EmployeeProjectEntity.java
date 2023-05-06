package com.dbproj.manageprompt.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Builder
@Table(
        name = "`employee_project`",
        uniqueConstraints={
                @UniqueConstraint(
                        name = "contstraint_emp_pro_id_unique",
                        columnNames = {"emp_pro_id"}
                )
        }
)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmployeeProjectEntity extends BaseTime {
  @Id
  @Column(name = "emp_pro_id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long empProId;

  @Column(name = "start_date", nullable = false)
  private Date startDate;

  @Column(name = "end_date", nullable = false)
  private Date endDate;

  @ManyToOne(fetch = FetchType.LAZY) // 직원 mapping (FK), 단방향
  @JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
  private EmployeeEntity employeeEntity;

  @ManyToOne(fetch = FetchType.LAZY) // 프로젝트 mapping (FK), 양방향
  @JoinColumn(name = "pro_id", referencedColumnName = "pro_id")
  private ProjectEntity projectEntity;

  @OneToMany(mappedBy = "employeeProjectEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL) // 직원_PM_평가 mapping (FK), 양방향
  private Set<EvaluationInnerEntity> evaluationInnerEntities;

  @OneToOne(fetch = FetchType.LAZY) // 직무 mapping (FK), 단방향
  @JoinColumn(name = "role_id", referencedColumnName = "role_id")
  private RoleEntity roleEntity;
}
