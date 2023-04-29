package com.dbproj.manageprompt.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@Table(name = "`project`")
@Getter
@Setter
@Entity
public class ProjectEntity extends BaseTime {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long pro_id;

  private Integer client_id;

  private String pro_name;

  private Date start_date;

  private Date end_date;

  private Integer budget;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "pro_id")
  private Set<EmployeeProjectEntity> employeeProjectEntities;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "client_id", referencedColumnName = "client_id")
  private ClientInfoEntity clientInfoEntity;
}
