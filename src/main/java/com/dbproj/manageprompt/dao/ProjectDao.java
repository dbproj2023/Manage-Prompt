package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.entity.ProjectEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProjectDao extends JpaRepository<ProjectEntity, String>, JpaSpecificationExecutor<ProjectEntity> {

    ProjectEntity findByProId(String keyword);
    ProjectEntity findByProName(String keyword);
    List<ProjectEntity> findAllByProNameContaining(String keyword);

}
