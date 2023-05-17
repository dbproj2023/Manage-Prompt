package com.dbproj.manageprompt.specification;

import com.dbproj.manageprompt.entity.ProjectEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class ProjectSpecification {
    // 년도 검색
//    public static Specification<ProjectEntity> equalYear(Integer year) {
//        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.function("YEAR", Integer.class, root.get("start_date"), year);
//    }
    // 기간 검색 (시작일 기준)
    public static Specification<ProjectEntity> searchStartDate(Date start_date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), start_date);
    }
    // 기간 검색 (종료일 기준)
    public static Specification<ProjectEntity> searchEndDate(Date end_date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), end_date);
    }
    // 상태 검색
    // 프로젝트명 검색
    public static Specification<ProjectEntity> equalProName(String proName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("proName"), proName);
    }
    // 발주처명 검색
    public static Specification<ProjectEntity> equalClientName(String clientName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("clientInfoEntity").get("clientName"), clientName);
    }
    // 발주 금액 구간 검색
    public static Specification<ProjectEntity> betweenBudget(Integer budget_start, Integer budget_end) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("budget"), budget_start, budget_end);
    }
    // 발주 금액 이상 개별 검색
    public static Specification<ProjectEntity> upperBudget(Integer budget) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("budget"), budget);
    }
    // 발주 금액 이하 개별 검색
    public static Specification<ProjectEntity> lowerBudget(Integer budget) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("budget"), budget);
    }
}
