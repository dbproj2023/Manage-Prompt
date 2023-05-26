package com.dbproj.manageprompt.dto;

import lombok.*;

import javax.persistence.Column;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ParticipantEvaluationResponseDto {
    @NotBlank
    @Column(name = "emp_id")
    private Long empId;

//    private String empName;
//
//    private String proName;
//
//    private String roleName;
//
//    private Date startDate;
//
//    private Date endDate;
//
//    private Date projStartDate;
//
//    private Date projEndDate;
//
//    private Long empProId;
//
//    private Double communicationRating;
//
//    private Double performanceRating;


//    private List<EmployeeProjectEvaluationResponseDto> projectEvaluationList;
//
//    public static ParticipantEvaluationResponseDto from(EmployeeEntity employeeEntity) {
//        return ParticipantEvaluationResponseDto.builder()
//                .empId(employeeEntity.getEmpId())
//                .empName(employeeEntity.getEmpName())
//                .projectEvaluationList(employeeEntity.getEmployeeProjectEntities().stream().map(EmployeeProjectEvaluationResponseDto::from).collect(Collectors.toList()))
//                .build();
//    }
}
