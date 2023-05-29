package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.entity.EmployeeProjectEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

public interface EmployeeProjectDao extends JpaRepository<EmployeeProjectEntity, Long> {
    @Query(
            value = "select * from employee_project where pro_id=:proId and emp_id=:empId",
            nativeQuery = true
    )
    EmployeeProjectEntity findByProjectEntity_ProIdAndAndEmployeeEntity_EmpId(@Param("proId") String proId,
                                                                              @Param("empId") Long empId);
}
