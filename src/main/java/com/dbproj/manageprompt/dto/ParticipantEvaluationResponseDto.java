package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.EmployeeEntity;
import com.dbproj.manageprompt.entity.EmployeeProjectEntity;
import com.dbproj.manageprompt.entity.ProjectEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ParticipantEvaluationResponseDto {
    @NotBlank
    private Long empId;

    private String empName;

    private List<EmployeeProjectEvaluationResponseDto> projectEvaluationList;

    public static ParticipantEvaluationResponseDto from(EmployeeEntity employeeEntity) {
        return ParticipantEvaluationResponseDto.builder()
                .empId(employeeEntity.getEmpId())
                .empName(employeeEntity.getEmpName())
                .projectEvaluationList(employeeEntity.getEmployeeProjectEntities().stream().map(EmployeeProjectEvaluationResponseDto::from).collect(Collectors.toList()))
                .build();
    }
}
