package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.Interface.WapperInterface;
import com.dbproj.manageprompt.dto.ProjectEmployeeSearchDto;
import com.dbproj.manageprompt.entity.EmployeeEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao extends JpaRepository<EmployeeEntity, Long>, JpaSpecificationExecutor<EmployeeEntity> {
    @Query("SELECT e FROM EmployeeEntity e ORDER BY e.empId ASC")
    List<EmployeeEntity> findAllAsc();
    //@Query("SELECT DISTINCT e FROM EmployeeEntity e WHERE e.empId NOT IN (SELECT DISTINCT ep.empProId FROM EmployeeProjectEntity ep WHERE ep.endDate > current_date())")
    //List<EmployeeEntity> findByWorkIn();
    @Query("select e.empId as emp_id, e.empName as emp_name,e.empEmail as emp_email ,e.empEdu as emp_edu, e.empSsn as emp_ssn, coalesce(p.proId, null) as pro_id,e.empWorkEx as emp_workex,e.empSkill as skill_name, coalesce(r.roleId, null) as role, coalesce(avg(ei.communicationRating),null) as com, coalesce(avg(ei.performanceRating),null) as per from EmployeeEntity e left join e.employeeProjectEntities ep left join ep.evaluationInnerEntities ei left join ep.projectEntity p left join ep.roleEntity r where e.empId = :empId group by e.empId, p.proId, r.roleId")
    WapperInterface findByQuery(Long empId);
    @Query("select e.empId as emp_id, count(e.empId) as proj_count from EmployeeEntity e left join e.employeeProjectEntities ep where ep.startDate <= current_date() and ep.endDate > current_date() and e.empId = :empId group by e.empId")
    WapperInterface findByQuery2(Long empId);
    EmployeeEntity findByEmpId(Long keyword);
    EmployeeEntity findByEmpEmail(String keyword);
    List<EmployeeEntity> findByEmpIdContaining(String keyword);
    List<EmployeeEntity> findByEmpNameContaining(String keyword);
    List<EmployeeEntity> findByEmpSkillContaining(String keyword);

    boolean existsByEmpId(Long emp_id);

    boolean existsByEmpEmail(String emp_email);
}
