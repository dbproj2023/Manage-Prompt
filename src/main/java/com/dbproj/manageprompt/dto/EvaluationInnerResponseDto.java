package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.EvaluationInnerEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EvaluationInnerResponseDto {
    private final Long evalId;
    private final Long evaluator;
    private final Integer communicationRating;
    private final String communicationDetail;
    private final Integer performanceRating;
    private final String performanceDetail;

    public static EvaluationInnerResponseDto from(EvaluationInnerEntity evaluationInnerEntity) {
        return new EvaluationInnerResponseDto(
                evaluationInnerEntity.getEvalId(),
                evaluationInnerEntity.getEvaluator(),
                evaluationInnerEntity.getPerformanceRating(),
                evaluationInnerEntity.getCommunicationDetail(),
                evaluationInnerEntity.getPerformanceRating(),
                evaluationInnerEntity.getPerformanceDetail()
        );
    }
}
