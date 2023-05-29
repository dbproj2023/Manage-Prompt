package com.dbproj.manageprompt.entity;

import lombok.*;

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
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccessInfoEntity extends BaseTime {
  @Id
  @Column(name = "access_grade", nullable = false)
  private Integer accessGrade;

  @Column(name = "discrete", nullable = false, length = 20)
  private String discrete;
}
