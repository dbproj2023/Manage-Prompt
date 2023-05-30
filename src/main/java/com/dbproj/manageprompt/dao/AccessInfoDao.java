package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.entity.AccessInfoEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccessInfoDao extends JpaRepository<AccessInfoEntity, Integer> {
    @Query(
            value = "select * from access_info where access_grade=:id",
            nativeQuery = true
    )
    Optional<AccessInfoEntity> findById(@Param("id") Integer id);
}
