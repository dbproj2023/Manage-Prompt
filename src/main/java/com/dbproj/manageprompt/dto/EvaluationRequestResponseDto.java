package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.EvaluationRequestEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EvaluationRequestResponseDto {
    private final Long cusEvalId;
    private final Integer communicationRating;
    private final String communicationDetail;
    private final Integer performanceRating;
    private final String performanceDetail;

    public static EvaluationRequestResponseDto from(EvaluationRequestEntity evaluationRequestEntity) {
        return new EvaluationRequestResponseDto(
                evaluationRequestEntity.getCusEvalId(),
                evaluationRequestEntity.getCommunicationRating(),
                evaluationRequestEntity.getCommunicationDetail(),
                evaluationRequestEntity.getPerformanceRating(),
                evaluationRequestEntity.getPerformanceDetail()
        );
    }
}
