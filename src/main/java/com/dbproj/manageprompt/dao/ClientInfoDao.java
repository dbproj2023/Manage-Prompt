package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.entity.ClientInfoEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

public interface ClientInfoDao extends JpaRepository<ClientInfoEntity, Long> {
    @Query(
            value = "select * from client_info where client_emp_name=:keyword",
            nativeQuery = true
    )
    ClientInfoEntity findByClientEmpName(@Param("keyword") String keyword);
    @Query(
            value = "select * from client_info where client_id=:keyword",
            nativeQuery = true
    )
    ClientInfoEntity findByClientId(@Param("keyword") Long keyword);
}
