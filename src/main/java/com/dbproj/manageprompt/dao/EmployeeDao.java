package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.Interface.WapperInterface;
import com.dbproj.manageprompt.Interface.WrapperInterface;

import com.dbproj.manageprompt.entity.EmployeeEntity;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeDao extends JpaRepository<EmployeeEntity, Long>, JpaSpecificationExecutor<EmployeeEntity> {
    @Query("SELECT e FROM EmployeeEntity e ORDER BY e.empId ASC")
    List<EmployeeEntity> findAllAsc();
    //@Query("SELECT DISTINCT e FROM EmployeeEntity e WHERE e.empId NOT IN (SELECT DISTINCT ep.empProId FROM EmployeeProjectEntity ep WHERE ep.endDate > current_date())")
    //List<EmployeeEntity> findByWorkIn();
    @Query("select e.empId as emp_id, e.empName as emp_name,e.empEmail as emp_email ,e.empEdu as emp_edu, e.empSsn as emp_ssn, coalesce(p.proId, null) as pro_id,e.empWorkEx as emp_workex,e.empSkill as skill_name, coalesce(r.roleId, null) as role, coalesce(avg(ei.communicationRating),null) as com, coalesce(avg(ei.performanceRating),null) as per from EmployeeEntity e left join e.employeeProjectEntities ep left join ep.evaluationInnerEntities ei left join ep.projectEntity p left join ep.roleEntity r where e.empId = :empId group by e.empId, p.proId, r.roleId")
    List<WapperInterface> findByQuery(@Param("empId") Long empId);
    @Query("select count(e.empId) as proj_count from EmployeeEntity e left join e.employeeProjectEntities ep where ep.startDate <= current_date() and ep.endDate >= current_date() and e.empId = :empId group by e.empId")
    List<WrapperInterface> findByQuery2(@Param("empId") Long empId);
    @Query("select e.empId as emp_id, e.empName as emp_name,e.empEmail as emp_email ,e.empEdu as emp_edu, e.empSsn as emp_ssn, coalesce(p.proId, null) as pro_id,e.empWorkEx as emp_workex,e.empSkill as skill_name, coalesce(r.roleId, null) as role, coalesce(avg(ei.communicationRating),null) as com, coalesce(avg(ei.performanceRating),null) as per from EmployeeEntity e left join e.employeeProjectEntities ep left join ep.evaluationInnerEntities ei left join ep.projectEntity p left join ep.roleEntity r where e.empId = :empId and (e.empId NOT IN (select e1.empId from EmployeeEntity e1 join e1.employeeProjectEntities ep1 where ep1.endDate >= current_date()) or e.empId NOT IN (select distinct e2.empId from EmployeeEntity e2 join e2.employeeProjectEntities ep2)) group by e.empId, p.proId, r.roleId")
    List<WapperInterface> findByQuery3(@Param("empId") Long empId);
    @Query(
            value = "select * from employee where emp_id=:empId",
            nativeQuery = true
    )
    EmployeeEntity findByEmpId(@Param("empId") Long empId);
    @Query(
            value = "select * from employee where emp_email=:email",
            nativeQuery = true
    )
    EmployeeEntity findByEmpEmail(@Param("email") String email);
    @Query(
            value = "select * from employee where emp_id like %:empId%",
            nativeQuery = true
    )
    List<EmployeeEntity> findByEmpIdContaining(@Param("empId") String empId);
    @Query(
            value = "select * from employee where emp_name like %:empName%",
            nativeQuery = true
    )
    List<EmployeeEntity> findByEmpNameContaining(@Param("empName") String empName);
    @Query(
            value = "select * from employee where emp_skill like %:empSkill%",
            nativeQuery = true
    )
    List<EmployeeEntity> findByEmpSkillContaining(@Param("empSkill") String empSkill);
    // 직원 정보 조회 (프로젝트 직원 추가용 검색 - 사번)

    // 직원 정보 조회 (프로젝트 직원 추가용 검색 - 이름)
    @Query(
            value = "select * from employee where emp_name like %:empName%",
            nativeQuery = true
    )
    List<EmployeeEntity> findAllByEmpName(@Param("empName") String empName);

    boolean existsByEmpId(Long emp_id);

    boolean existsByEmpEmail(String emp_email);
}
