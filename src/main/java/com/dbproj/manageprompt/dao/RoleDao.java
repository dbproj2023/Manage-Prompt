package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.entity.RoleEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<RoleEntity, Long> {

    RoleEntity findByRoleId(Long keyword);
    RoleEntity findByRoleName(String keyword);
}
