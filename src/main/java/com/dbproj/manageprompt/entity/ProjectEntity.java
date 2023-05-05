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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long pro_id;

  @Column(name = "pro_name", nullable = false) // length 255
  private String pro_name;

  @Column(name = "start_date", nullable = false)
  private Date start_date;

  @Column(name = "end_date", nullable = false)
  private Date end_date;

  @Column(name = "budget", nullable = false) // 예산
  private Integer budget;

  @OneToOne(fetch = FetchType.LAZY) // 발주처 mapping (FK), 단방향
  @JoinColumn(name = "client_id", referencedColumnName = "client_id")
  private ClientInfoEntity clientInfoEntity;
}
