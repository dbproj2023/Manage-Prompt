package com.dbproj.manageprompt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmployeeProjectEntity extends BaseTime {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long emp_pro_id;

  @Column(name = "start_date", nullable = false)
  private Date start_date;

  @Column(name = "end_date", nullable = false)
  private Date end_date;

  @ManyToOne(fetch = FetchType.LAZY) // 직원 mapping (FK), 단방향
  @JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
  private EmployeeEntity employeeEntity;

  @ManyToOne(fetch = FetchType.LAZY) // 프로젝트 mapping (FK), 단방향
  @JoinColumn(name = "pro_id", referencedColumnName = "pro_id")
  private ProjectEntity projectEntity;

  @OneToMany(mappedBy = "eval_id", fetch = FetchType.LAZY) // 직원_PM_평가 mapping (FK), 양방향
  private Set<EvaluationInnerEntity> evaluationInnerEntities;

  @OneToOne(fetch = FetchType.LAZY) // 직무 mapping (FK), 단방향
  @JoinColumn(name = "role_id", referencedColumnName = "role_id")
  private RoleEntity roleEntity;
}
