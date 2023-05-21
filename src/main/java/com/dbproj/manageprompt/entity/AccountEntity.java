package com.dbproj.manageprompt.entity;

import com.dbproj.manageprompt.dto.AccountCreateRequestDto;
import lombok.*;

import javax.persistence.*;

@Builder
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
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountEntity extends BaseTime {
  @Id
  @Column(name = "acc_id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long accId;

  @Column(name = "auth_id", nullable = false, length = 50)
  private String authId;

  @Column(name = "auth_pw", nullable = false) // length = 255
  private String authPw;

  @OneToOne(fetch = FetchType.LAZY) // 직원 mapping (FK), 단반향
  @JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
  private EmployeeEntity employeeEntity;

  @OneToOne(fetch = FetchType.LAZY) // 계정정보 mapping (FK), 단반향
  @JoinColumn(name = "access_grade", referencedColumnName = "access_grade")
  private AccessInfoEntity accessInfoEntity;

  public static AccountEntity toUpdateAccountEntity(AccountCreateRequestDto memberDTO) {
    AccountEntity accountEntity = new AccountEntity();
    accountEntity.setAccId(memberDTO.getAcc_id());
    accountEntity.setAuthId(memberDTO.getAuth_id());
    accountEntity.setAuthPw(memberDTO.getAuth_pw());
    accountEntity.setEmployeeEntity(memberDTO.getEmployee());
    accountEntity.setAccessInfoEntity(memberDTO.getAccess_Info());
    return accountEntity;
  }

    public void update(String new_pw) {
      if (new_pw != null) {
        this.authPw = new_pw;
      }
    }
    public void updateAccess(AccessInfoEntity accessInfoEntity) {
      this.accessInfoEntity = accessInfoEntity;
    }
}
