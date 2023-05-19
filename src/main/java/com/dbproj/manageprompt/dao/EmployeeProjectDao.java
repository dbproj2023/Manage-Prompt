package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.entity.EmployeeProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeProjectDao extends JpaRepository<EmployeeProjectEntity, Long> {

    EmployeeProjectEntity findByProjectEntity_ProIdAndAndEmployeeEntity_EmpId(String proId, Long empId);
}
