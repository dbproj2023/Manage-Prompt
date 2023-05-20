package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.entity.EvaluationRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRequestDao extends JpaRepository<EvaluationRequestEntity, Long> {
}
