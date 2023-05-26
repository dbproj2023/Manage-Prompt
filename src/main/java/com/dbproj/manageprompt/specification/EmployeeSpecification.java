package com.dbproj.manageprompt.specification;

import com.dbproj.manageprompt.entity.EmployeeEntity;
import com.dbproj.manageprompt.entity.EmployeeProjectEntity;
import com.dbproj.manageprompt.entity.ProjectEntity;
import com.dbproj.manageprompt.entity.RoleEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.Date;

public class EmployeeSpecification {
    public static Specification<EmployeeEntity> equalId(String empId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("empId"), empId);
    }
    public static Specification<EmployeeEntity> equalName(String empName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("empName"), empName);
    }
    public static Specification<EmployeeEntity> equalSkill(String empSkill) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("empSkill"), empSkill);
    }
    public static Specification<EmployeeEntity> equalProId(String pro_id) {
        return (root, query, criteriaBuilder) -> {
            Join<EmployeeEntity, EmployeeProjectEntity> empProj = root.join("employeeProjectEntities");
            Join<EmployeeProjectEntity,ProjectEntity> projProj = empProj.join("projectEntity");
            return criteriaBuilder.equal(projProj.get("proId"), pro_id);
        };
    }
    public static Specification<EmployeeEntity> equalRole(Long role) {
        return (root,query,criteriaBuilder) -> {
            Join<EmployeeEntity, EmployeeProjectEntity> empProj = root.join("employeeProjectEntities");
            Join<EmployeeProjectEntity, RoleEntity> projRole = empProj.join("roleEntity");
            return criteriaBuilder.equal(projRole.get("roleId"),role);
        };
    }
    public static Specification<EmployeeEntity> afterDate(Date period_start) {
        return (root,query,criteriaBuilder) -> {
            Join<EmployeeEntity, EmployeeProjectEntity> empProj = root.join("employeeProjectEntities");
            return criteriaBuilder.greaterThanOrEqualTo(empProj.get("startDate"),period_start);
        };
    }
    public static Specification<EmployeeEntity> beforeDate(Date period_end) {
        return (root,query,criteriaBuilder) -> {
            Join<EmployeeEntity, EmployeeProjectEntity> empProj = root.join("employeeProjectEntities");
            return criteriaBuilder.lessThanOrEqualTo(empProj.get("endDate"),period_end);
        };
    }
    public static Specification<EmployeeEntity> isWork(Date date) {
        return (root, query, criteriaBuilder) -> {
            Join<EmployeeEntity, EmployeeProjectEntity> empProj = root.join("employeeProjectEntities", JoinType.LEFT);
            Predicate predicate = criteriaBuilder.isNull(empProj.get("empProId"));
            Predicate predicate1 = criteriaBuilder.lessThanOrEqualTo(empProj.get("endDate"), date);
            return criteriaBuilder.or(predicate,predicate1);
        };
    }
    public static Specification<EmployeeEntity> all(String a) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("empName"), a).not();
    }

}
