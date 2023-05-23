package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.entity.EmployeeEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeDao extends JpaRepository<EmployeeEntity, Long>, JpaSpecificationExecutor<EmployeeEntity> {
    @Query("SELECT e FROM EmployeeEntity e ORDER BY e.empId ASC")
    List<EmployeeEntity> findAllAsc();
    List<EmployeeEntity> findByProjectEmployee();
    EmployeeEntity findByEmpId(Long keyword);
    List<EmployeeEntity> findByEmpIdContaining(String keyword);
    List<EmployeeEntity> findByEmpNameContaining(String keyword);
    List<EmployeeEntity> findByEmpSkillContaining(String keyword);

    boolean existsByEmpId(Long emp_id);

    boolean existsByEmpEmail(String emp_email);
}
