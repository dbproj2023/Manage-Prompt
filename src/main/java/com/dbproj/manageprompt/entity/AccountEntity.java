package com.dbproj.manageprompt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(
        name = "`account`",
        uniqueConstraints={
                @UniqueConstraint(
                        name = "contstraint_emp_id_unique",
                        columnNames = {"acc_id"}
                )
        }
)
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountEntity extends BaseTime {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long acc_id;

  @Column(name = "auth_id", nullable = false, length = 50)
  private String auth_id;

  @Column(name = "auth_pw", nullable = false) // length = 255
  private String auth_pw;

  @OneToOne(fetch = FetchType.LAZY) // 직원 mapping (FK), 단반향
  @JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
  private EmployeeEntity employeeEntity;

  @OneToOne(fetch = FetchType.LAZY) // 계정정보 mapping (FK), 단반향
  @JoinColumn(name = "access_grade", referencedColumnName = "access_grade")
  private AccessInfoEntity accessInfoEntity;
}
