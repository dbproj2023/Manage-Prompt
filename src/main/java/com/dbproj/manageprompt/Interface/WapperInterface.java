package com.dbproj.manageprompt.Interface;

import lombok.Setter;


public interface WapperInterface {
    Long getRole();
    String getPro_id();
    String getSkill_name();
    Long getEmp_id();
    String getEmp_name();
    String getEmp_ssn();
    String getEmp_email();
    String getEmp_edu();
    Integer getEmp_workex();
    Integer getCom();
    Integer getPer();
    Integer getProj_count();
}
