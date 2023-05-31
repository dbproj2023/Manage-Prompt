package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.dto.ClientEvalResponseInterface;
import com.dbproj.manageprompt.entity.EvaluationRequestEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface EvaluationRequestDao extends JpaRepository<EvaluationRequestEntity, Long> {
    @Query(
            value = "select * from evaluation_request where client_id=(select client_id from client_info where client_name=:clientName)",
            nativeQuery = true
    )
    EvaluationRequestEntity findByClientInfoEntity_clientName(@Param("clientName") String clientName);

    @Query(
            value = "select * from ((select pro_id from employee_project where emp_id=:empId) as ep natural join (select pro_id, client_id from project) as p) natural join (select client_id, communication_rating,performance_rating, communication_detail, performance_detail from evaluation_request natural join client_info) as pj",
            nativeQuery = true
    )
    List<ClientEvalResponseInterface> findAllByEmpName(@Param("empId") Long empId);
}
