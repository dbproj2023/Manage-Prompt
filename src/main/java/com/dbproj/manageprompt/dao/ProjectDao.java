package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.entity.ProjectEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectDao extends JpaRepository<ProjectEntity, String> {

    ProjectEntity findByProName(String keyword);
    List<ProjectEntity> findAllByProNameContaining(String keyword);
}
