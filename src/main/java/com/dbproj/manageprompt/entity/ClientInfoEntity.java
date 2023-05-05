package com.dbproj.manageprompt.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@Table(
        name = "`client_info`",
        uniqueConstraints={
                @UniqueConstraint(
                        name = "contstraint_client_id_unique",
                        columnNames = {"client_Id"}
                )
        }
)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientInfoEntity extends BaseTime {
  @Id
  @Column(name = "client_Id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long clientId;

  @Column(name = "client_name", nullable = false, length = 50)
  private String clientName;

  @Column(name = "client_emp_name", nullable = false, length = 20)
  private String clientEmpName;

  @Column(name = "client_emp_ph", nullable = false, length = 20)
  private String clientEmpPh;

  @Column(name = "client_emp_email", nullable = false, length = 30)
  private String clientEmpEmail;

  public void update(String clientEmpName, String clientEmpPh, String clientEmpEmail) {
    if (clientEmpName != null) {
      this.clientEmpName = clientEmpName;
    }
    if (clientEmpPh != null) {
      this.clientEmpPh = clientEmpPh;
    }
    if (clientEmpEmail != null) {
      this.clientEmpEmail = clientEmpEmail;
    }
  }
}
