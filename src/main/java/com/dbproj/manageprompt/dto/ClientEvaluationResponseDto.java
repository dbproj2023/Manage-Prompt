//package com.dbproj.manageprompt.dto;
//
//import com.dbproj.manageprompt.entity.ClientInfoEntity;
//
//import lombok.*;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Data
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
//@Getter
//public class ClientEvaluationResponseDto {
//    private Long clientId;
//
//    private String clientName;
//
//    private List<EmployeeProjectEvaluationResponseDto> projectEvaluationList;
//
//    public static ClientEvaluationResponseDto from(ClientInfoEntity clientInfoEntity) {
//        return ClientEvaluationResponseDto.builder()
//                .clientId(clientInfoEntity.getClientId())
//                .clientName(clientInfoEntity.getClientName())
//
//                .projectEvaluationList(clientInfoEntity.getEmployeeProjectEntities().stream().map(EmployeeProjectEvaluationResponseDto::from).collect(Collectors.toList()))
//                .build();
//    }
//}
