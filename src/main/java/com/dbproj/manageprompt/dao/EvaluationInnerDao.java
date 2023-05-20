package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.entity.EvaluationInnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationInnerDao extends JpaRepository<EvaluationInnerEntity, Long> {
}
