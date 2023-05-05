package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeDao extends JpaRepository<EmployeeEntity, Long> {
    List<EmployeeEntity> findByEmpIdContaining(String keyword);
    List<EmployeeEntity> findByEmpNameContaining(String keyword);
    List<EmployeeEntity> findByEmpSkillContaining(String keyword);
}
