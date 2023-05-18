package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.entity.EmployeeEntity;
import org.springframework.data.jpa.domain.Specification;

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
}
