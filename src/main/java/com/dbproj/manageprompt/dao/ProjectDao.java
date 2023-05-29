package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.dto.ProjectAggNumBudgetByYearResponseInterface;
import com.dbproj.manageprompt.entity.ProjectEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectDao extends JpaRepository<ProjectEntity, String>, JpaSpecificationExecutor<ProjectEntity> {

    // 년도별 프로젝트 수행 횟수 및 총 발주 금액
    @Query(
            value = "select YEAR(start_date) as year, count(*) as cnt, sum(budget) as total_budget from project where YEAR(start_date)=:year group by YEAR(start_date)",
            nativeQuery = true
    )
    ProjectAggNumBudgetByYearResponseInterface findSumNumAndBudgetByYear(@Param("year") Integer year);

    @Query(
            value = "select * from project where pro_id=:proId",
            nativeQuery = true
    )
    ProjectEntity findByProId(@Param("proId") String proId);
}
