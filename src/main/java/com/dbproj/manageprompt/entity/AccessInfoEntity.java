package com.dbproj.manageprompt.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Table(name = "`access_info`")
@Getter
@Setter
@Entity
public class AccessInfoEntity extends BaseTime {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private Integer access_grade;

  private String discrete;
}
