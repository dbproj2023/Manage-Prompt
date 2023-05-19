package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.entity.AccessInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessInfoDao extends JpaRepository<AccessInfoEntity, Integer> {
}
