package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.entity.ClientInfoEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientInfoDao extends JpaRepository<ClientInfoEntity, Long> {
}
