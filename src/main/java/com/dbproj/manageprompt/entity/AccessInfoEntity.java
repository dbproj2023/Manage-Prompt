package com.dbproj.manageprompt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(
        name = "`access_info`",
        uniqueConstraints={
                @UniqueConstraint(
                        name = "contstraint_access_grade_unique",
                        columnNames = {"access_grade"}
                )
        }
)
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccessInfoEntity extends BaseTime {
  @Id
  private Integer access_grade;

  @Column(name = "discrete", nullable = false, length = 20)
  private String discrete;
}
