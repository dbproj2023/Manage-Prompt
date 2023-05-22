package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.EmployeeProjectEntity;
import com.dbproj.manageprompt.entity.EvaluationInnerEntity;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ParticipantEvaluationCreateRequestDto {
    @NotBlank
    private String pro_id;

    @NotBlank
    private Long coworker_emp_id; // 피평가자 ID

    private Long evaluator; // 평가자 ID

    @NotBlank
    private Integer communication_rating;

    @NotBlank
    private String communication_desc;

    @NotBlank
    private Integer performance_rating;

    @NotBlank
    private String performance_desc;

    private EmployeeProjectEntity employeeProject;

    public EvaluationInnerEntity toEntity() {
        EvaluationInnerEntity evalInner = EvaluationInnerEntity.builder()
                .evaluator(evaluator)
                .communicationRating(communication_rating)
                .communicationDetail(communication_desc)
                .performanceRating(performance_rating)
                .performanceDetail(performance_desc)
                .employeeProjectEntity(employeeProject)
                .build();

        return evalInner;
    }
}
