package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.entity.RoleEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

public interface RoleDao extends JpaRepository<RoleEntity, Long> {

    @Query(
            value = "select * from role_info where role_id=:roleId",
            nativeQuery = true
    )
    RoleEntity findByRoleId(@Param("roleId") Long roleId);
}
