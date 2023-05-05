package com.dbproj.manageprompt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Table(
        name = "`client_info`",
        uniqueConstraints={
                @UniqueConstraint(
                        name = "contstraint_client_id_unique",
                        columnNames = {"client_id"}
                )
        }
)
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientInfoEntity extends BaseTime {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long client_id;

  @Column(name = "client_name", nullable = false, length = 50)
  private String client_name;

  @Column(name = "client_emp_name", nullable = false, length = 20)
  private String client_emp_name;

  @Column(name = "client_emp_ph", nullable = false, length = 20)
  private String client_emp_ph;

  @Column(name = "client_emp_email", nullable = false, length = 30)
  private String client_emp_email;
}
