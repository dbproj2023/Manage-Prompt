package com.dbproj.manageprompt.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@Table(name = "`account`")
@Getter
@Setter
@Entity
public class AccountEntity extends BaseTime {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long emp_id;

  private Integer access_grade;

  private String auth_id;

  private String auth_pw;
}
