package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.EvaluationRequestEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EvaluationRequestResponseDto {
    private final Long cus_eval_id;
    private final Integer communication_rating;
    private final String communication_detail;
    private final Integer performance_rating;
    private final String performance_detail;

    public static EvaluationRequestResponseDto from(EvaluationRequestEntity evaluationRequestEntity) {
        return new EvaluationRequestResponseDto(
                evaluationRequestEntity.getCus_eval_id(),
                evaluationRequestEntity.getCommunication_rating(),
                evaluationRequestEntity.getCommunication_detail(),
                evaluationRequestEntity.getPerformance_rating(),
                evaluationRequestEntity.getPerformance_detail()
        );
    }
}
