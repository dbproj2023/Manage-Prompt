package com.dbproj.manageprompt.service;

import com.dbproj.manageprompt.dao.EmployeeDao;
import com.dbproj.manageprompt.dto.AccountCreateRequestDto;
import com.dbproj.manageprompt.dto.EmployeeRequestDto;
import com.dbproj.manageprompt.dto.EmployeeResponseDto;
import com.dbproj.manageprompt.entity.AccountEntity;
import com.dbproj.manageprompt.specification.EmployeeSpecification;
import com.dbproj.manageprompt.entity.EmployeeEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmployeeService {

    private final EmployeeDao employeeDao;

    //직원 정보 조회(개인)


    // 직원 검색 Service
    // 직무, 프로젝트 이름, 스킬이름, 프로젝트 참여 여부로 직원을 검색
//    @Transactional(readOnly = true)
//    public List<EmployeeEntity> findAllByProj(String role, String pro_name, String skill_name, Integer is_work) {
//        return employeeDao.findByRoleAndProjAndSkillAndPeriod();
//    }

    // 프로젝트 추가용 직원 검색 Service
    // keyword: 조회할 사번, 이름, 스킬
    // searchOption : 사번
    public List<EmployeeEntity> getEmployeeSearch(String empId, String empName, String empSkill) {
        Specification<EmployeeEntity> spec = (root, query, criteriaBuilder) -> null;
        log.info(String.valueOf(empId==""));
        if(empId != "")
            spec = spec.and(EmployeeSpecification.equalId(empId));
        if(empName != "")
            spec = spec.and(EmployeeSpecification.equalName(empName));
        if(empSkill != "")
            spec = spec.and(EmployeeSpecification.equalSkill(empSkill));
        //if(empId == "" && empName == "" && empSkill == "")
        //   return employeeDao.
        return employeeDao.findAll(spec);
    }

    @Transactional(readOnly = true)
    public List<EmployeeEntity> findByEmpId(String keyword) {
        return employeeDao.findByEmpIdContaining(keyword);
    }
    // searchOption : 이름
    @Transactional(readOnly = true)
    public List<EmployeeEntity> findByEmpName(String keyword) {
        return employeeDao.findByEmpNameContaining(keyword);
    }
    // searchOption : 스킬
    @Transactional(readOnly = true)
    public List<EmployeeEntity> findByEmpSkill(String keyword) {
        return employeeDao.findByEmpSkillContaining(keyword);
    }


}
