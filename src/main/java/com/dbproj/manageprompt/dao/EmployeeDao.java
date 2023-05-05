package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeDao extends JpaRepository<EmployeeEntity, Long> {
    List<EmployeeEntity> findByEmp_idContaining(String keyword);
    List<EmployeeEntity> findByEmp_nameContaining(String keyword);
    List<EmployeeEntity> findByEmp_skillContaining(String keyword);
}
