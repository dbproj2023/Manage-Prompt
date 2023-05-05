package com.dbproj.manageprompt.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Builder
@Table(
        name = "`project`",
        uniqueConstraints={
                @UniqueConstraint(
                        name = "contstraint_pro_id_unique",
                        columnNames = {"pro_id"}
                )
        }
)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectEntity extends BaseTime {
  @Id
  @Column(name = "pro_id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long proId;

  @Column(name = "pro_name", nullable = false) // length 255
  private String proName;

  @Column(name = "start_date", nullable = false)
  private Date startDate;

  @Column(name = "end_date", nullable = false)
  private Date endDate;

  @Column(name = "budget", nullable = false) // 예산
  private Integer budget;

  @OneToOne(fetch = FetchType.LAZY) // 발주처 mapping (FK), 단방향
  @JoinColumn(name = "client_id", referencedColumnName = "client_id")
  private ClientInfoEntity clientInfoEntity;

  @OneToMany(mappedBy = "empProId", fetch = FetchType.LAZY) // 직원_프로젝트 mapping (FK), 양방향
  private Set<EmployeeProjectEntity> employeeProjectEntities;

  public void update(Date startDate, Date endDate, Integer budget) {
    if (startDate != null) {
      this.startDate = startDate;
    }
    if (endDate != null) {
      this.endDate = endDate;
    }
    if (budget != null) {
      this.budget = budget;
    }
  }
}
