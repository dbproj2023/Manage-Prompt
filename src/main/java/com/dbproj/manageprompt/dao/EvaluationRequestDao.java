package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.entity.EvaluationRequestEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

public interface EvaluationRequestDao extends JpaRepository<EvaluationRequestEntity, Long> {
    @Query(
            value = "select * from evaluation_request where client_id=(select client_id from client_info where client_name=:clientName)",
            nativeQuery = true
    )
    EvaluationRequestEntity findByClientInfoEntity_clientName(@Param("clientName") String clientName);
}
