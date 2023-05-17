package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.entity.EmployeeProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeProjectDao extends JpaRepository<EmployeeProjectEntity, Long> {

    EmployeeProjectEntity findByProjectEntity_ProNameAndAndEmployeeEntity_EmpId(String proName, Long empId);
}
