package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.dto.AccountRequestDto;
import com.dbproj.manageprompt.dto.EmployeeResponseDto;
import com.dbproj.manageprompt.entity.AccountEntity;
import com.dbproj.manageprompt.entity.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface EmployeeDao extends JpaRepository<EmployeeEntity, Long>, JpaSpecificationExecutor<EmployeeEntity> {
    //List<EmployeeEntity> findAll(Specification<EmployeeEntity> spec);
    List<EmployeeEntity> findByEmpIdContaining(String keyword);
    List<EmployeeEntity> findByEmpNameContaining(String keyword);
    List<EmployeeEntity> findByEmpSkillContaining(String keyword);
    //Optional<EmployeeEntity> findByAccount(AccountEntity accountEntity);
}
