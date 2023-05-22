package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.ClientInfoEntity;
import com.dbproj.manageprompt.entity.EmployeeProjectEntity;
import com.dbproj.manageprompt.entity.EvaluationInnerEntity;
import com.dbproj.manageprompt.entity.EvaluationRequestEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ClientEvaluationCreateRequestDto {
    @NotBlank
    private String pro_id;

    @NotBlank
    private Integer communication_rating;

    @NotBlank
    private String communication_desc;

    @NotBlank
    private Integer performance_rating;

    @NotBlank
    private String performance_desc;

    private ClientInfoEntity clientInfo;

    public EvaluationRequestEntity toEntity() {
        EvaluationRequestEntity evalInner = EvaluationRequestEntity.builder()
                .communicationRating(communication_rating)
                .communicationDetail(communication_desc)
                .performanceRating(performance_rating)
                .performanceDetail(performance_desc)
                .clientInfoEntity(clientInfo)
                .build();

        return evalInner;
    }
}
