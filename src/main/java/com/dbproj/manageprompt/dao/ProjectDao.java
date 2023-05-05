package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectDao extends JpaRepository<ProjectEntity, Long> {
}
