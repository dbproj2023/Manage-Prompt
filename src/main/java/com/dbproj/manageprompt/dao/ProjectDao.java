package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.Interface.ProjectSearchResponseInterface;
import com.dbproj.manageprompt.dto.ProjectAggNumBudgetByYearResponseInterface;
import com.dbproj.manageprompt.entity.ProjectEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ProjectDao extends JpaRepository<ProjectEntity, String> {
    //    public interface ProjectDao extends JpaRepository<ProjectEntity, String>, JpaSpecificationExecutor<ProjectEntity> {
    @Query(
            value = "select pro_name, start_date, end_date, budget, client_name from (select pro_id, pro_name, start_date, end_date, budget, client_id from project) as p natural join (select client_id, client_name from client_info) as c",
            nativeQuery = true
    )
    List<ProjectSearchResponseInterface> findAllNonParams();

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

    // 프로젝트 전체 조회
    @Query(
            value = "select pro_name, start_date, end_date, budget, client_name from (select pro_id, pro_name, start_date, end_date, budget, client_id from project) as p natural join (select client_id, client_name from client_info) as c" +
                    "  where pro_name like %:proName% and client_name like %:clientName% and start_date >=:startDate and end_date <=:endDate and budget >=:budgeStart and budget <=:budgeEnd",
            nativeQuery = true
    )
    List<ProjectSearchResponseInterface> findAll(
            @Param("proName") String proName,
            @Param("clientName") String clientName,
            @Param("budgeStart") Integer budgeStart,
            @Param("budgeEnd") Integer budgeEnd,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

    @Query(
            value = "select pro_name, start_date, end_date, budget, client_name from (select pro_id, pro_name, start_date, end_date, budget, client_id from project) as p natural join (select client_id, client_name from client_info) as c" +
                    "  where pro_name like %:proName% and client_name like %:clientName% and budget >=:budgeStart",
            nativeQuery = true
    )
    List<ProjectSearchResponseInterface> findAllNotEndBudge(
            @Param("proName") String proName,
            @Param("clientName") String clientName,
            @Param("budgeStart") Integer budgeStart
    );

    @Query(
            value = "select pro_name, start_date, end_date, budget, client_name from (select pro_id, pro_name, start_date, end_date, budget, client_id from project) as p natural join (select client_id, client_name from client_info) as c" +
                    "  where pro_name like %:proName% and client_name like %:clientName% and budget >=:budgeStart and budget <=:budgeEnd",
            nativeQuery = true
    )
    List<ProjectSearchResponseInterface> findAllNotDate(
            @Param("proName") String proName,
            @Param("clientName") String clientName,
            @Param("budgeStart") Integer budgeStart,
            @Param("budgeEnd") Integer budgeEnd
    );
    @Query(
            value = "select pro_name, start_date, end_date, budget, client_name from (select pro_id, pro_name, start_date, end_date, budget, client_id from project) as p natural join (select client_id, client_name from client_info) as c" +
                    "  where pro_name like %:proName% and client_name like %:clientName% and start_date >=:startDate and budget >=:budgeStart and budget <=:budgeEnd",
            nativeQuery = true
    )
    List<ProjectSearchResponseInterface> findAllIncludeStartDate(
            @Param("proName") String proName,
            @Param("clientName") String clientName,
            @Param("budgeStart") Integer budgeStart,
            @Param("budgeEnd") Integer budgeEnd,
            @Param("startDate") Date startDate
    );
    @Query(
            value = "select pro_name, start_date, end_date, budget, client_name from (select pro_id, pro_name, start_date, end_date, budget, client_id from project) as p natural join (select client_id, client_name from client_info) as c" +
                    "  where pro_name like %:proName% and client_name like %:clientName% and end_date <=:endDate and budget >=:budgeStart and budget <=:budgeEnd",
            nativeQuery = true
    )
    List<ProjectSearchResponseInterface> findAllIncludeEndDate(
            @Param("proName") String proName,
            @Param("clientName") String clientName,
            @Param("budgeStart") Integer budgeStart,
            @Param("budgeEnd") Integer budgeEnd,
            @Param("endDate") Date endDate
    );
}
