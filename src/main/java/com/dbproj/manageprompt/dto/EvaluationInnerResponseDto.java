package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.EvaluationInnerEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EvaluationInnerResponseDto {
    private final Long eval_id;
    private final Long evaluator;
    private final Integer communication_rating;
    private final String communication_detail;
    private final Integer performance_rating;
    private final String performance_detail;

    public static EvaluationInnerResponseDto from(EvaluationInnerEntity evaluationInnerEntity) {
        return new EvaluationInnerResponseDto(
                evaluationInnerEntity.getEval_id(),
                evaluationInnerEntity.getEvaluator(),
                evaluationInnerEntity.getCommunication_rating(),
                evaluationInnerEntity.getCommunication_detail(),
                evaluationInnerEntity.getPerformance_rating(),
                evaluationInnerEntity.getPerformance_detail()
        );
    }
}
