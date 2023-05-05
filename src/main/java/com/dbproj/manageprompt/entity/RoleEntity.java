package com.dbproj.manageprompt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(
        name = "`role`",
        uniqueConstraints={
                @UniqueConstraint(
                        name = "contstraint_role_id_unique",
                        columnNames = {"role_id"}
                )
        }
)
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleEntity extends BaseTime {
  @Id
  @Column(name = "role_id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long roleId;

  @Column(name = "role_name", nullable = false, length = 20)
  private String roleName;
}
