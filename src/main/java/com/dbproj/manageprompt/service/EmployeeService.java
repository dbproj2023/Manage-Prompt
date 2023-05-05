package com.dbproj.manageprompt.service;

import com.dbproj.manageprompt.dao.EmployeeDao;
import com.dbproj.manageprompt.entity.EmployeeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeDao employeeDao;

    // 직원 검색 Service
    // 직무, 프로젝트 이름, 스킬이름, 프로젝트 참여 여부로 직원을 검색
//    @Transactional(readOnly = true)
//    public List<EmployeeEntity> findAllByProj(String role, String pro_name, String skill_name, Integer is_work) {
//        return employeeDao.findByRoleAndProjAndSkillAndPeriod();
//    }

    // 프로젝트 추가용 직원 검색 Service
    // keyword: 조회할 사번, 이름, 스킬
    // searchOption : 사번
    @Transactional(readOnly = true)
    public List<EmployeeEntity> findByEmpId(String keyword) {
        return employeeDao.findByEmp_idContaining(keyword);
    }
    // searchOption : 이름
    @Transactional(readOnly = true)
    public List<EmployeeEntity> findByEmpName(String keyword) {
        return employeeDao.findByEmp_nameContaining(keyword);
    }
    // searchOption : 스킬
    @Transactional(readOnly = true)
    public List<EmployeeEntity> findByEmpSkill(String keyword) {
        return employeeDao.findByEmp_skillContaining(keyword);
    }
}
