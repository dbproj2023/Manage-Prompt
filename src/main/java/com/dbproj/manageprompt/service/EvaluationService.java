package com.dbproj.manageprompt.service;

import com.dbproj.manageprompt.dao.*;
import com.dbproj.manageprompt.dto.ClientEvaluationCreateRequestDto;
import com.dbproj.manageprompt.dto.ParticipantEvaluationCreateRequestDto;
import com.dbproj.manageprompt.dto.ParticipantEvaluationResponseDto;
import com.dbproj.manageprompt.entity.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j

public class EvaluationService {
    private final ProjectDao projectDao;

    private final EvaluationInnerDao evaluationInnerDao;
    private final EvaluationRequestDao evaluationRequestDao;
    private final EmployeeProjectDao employeeProjectDao;
    private final ClientInfoDao clientInfoDao;
    private final AccountDao accountDao;
    private final EmployeeDao employeeDao;


    // 동료 평가 등록
    public Map coworkEvalcreate(Long addId, ParticipantEvaluationCreateRequestDto requestDto) {
        Optional<AccountEntity> accountEntity = accountDao.findByaccId(addId);
        AccountEntity account = accountEntity.get();
        Long empId = account.getEmployeeEntity().getEmpId();

        // 피평가자 직원의 직원프로젝트 조회
        EmployeeProjectEntity empProj = employeeProjectDao.
                findByProjectEntity_ProNameAndAndEmployeeEntity_EmpId(
                        requestDto.getPro_name(),
                        requestDto.getCoworker_emp_id()
                );

        if (empProj == null) {
            Map response = new HashMap<String, Object>();
            response.put("message", "관련 정보가 없습니다.");
            response.put("status", 0);

            return response;
        }

        EvaluationInnerEntity eval = evaluationInnerDao.findByEvaluator(empId);
        if (eval != null) {
            Map response = new HashMap<String, Object>();
            response.put("message", "이미 평가한 회원입니다.");
            response.put("status", 0);

            return response;
        }

        requestDto.setEvaluator(empId); // 평가자
        requestDto.setCommunication_rating(requestDto.getCommunication_rating());
        requestDto.setCommunication_desc(requestDto.getCommunication_desc());
        requestDto.setPerformance_rating(requestDto.getPerformance_rating());
        requestDto.setPerformance_desc(requestDto.getPerformance_desc());
        requestDto.setEmployeeProject(empProj); // 피평가자의 직원프로젝트

        EvaluationInnerEntity newEval = requestDto.toEntity();
        newEval = evaluationInnerDao.save(newEval);

        Map response = new HashMap<String, Object>();
        response.put("message", "프로젝트에 평가가 등록되었습니다.");
        response.put("status", 1);
        response.put("eval_id", newEval.getEvalId());
        response.put("emp_pro_id", empProj.getEmpProId());

        return response;
    }

    // 고객 평가 등록
    public Map clientEvalcreate(Long addId, ClientEvaluationCreateRequestDto requestDto) {
        // 고객 ID (고객 사번)
        Optional<AccountEntity> accountEntity = accountDao.findByaccId(addId);
        AccountEntity account = accountEntity.get();
        String clientName = account.getEmployeeEntity().getEmpName();

        // 고객 정보
        ClientInfoEntity client = clientInfoDao.findByClientEmpName(clientName);
        if (client == null) {
            Map response = new HashMap<String, Object>();
            response.put("message", "관련 고객 정보가 없습니다.");
            response.put("status", 0);
            response.put("clientName", clientName);

            return response;
        }

        // 고객의 프로젝트 조회
        ProjectEntity project = projectDao.findByProName(requestDto.getPro_name());
        if (project == null) {
            Map response = new HashMap<String, Object>();
            response.put("message", "관련 정보가 없습니다.");
            response.put("status", 0);

            return response;
        }

        // 평가 여부 확인
        EvaluationRequestEntity evalClient = evaluationRequestDao.findByClientInfoEntity_clientName(clientName);
        if (evalClient != null) {
            Map response = new HashMap<String, Object>();
            response.put("message", "이미 평가하였습니다.");
            response.put("status", 0);

            return response;
        }

        requestDto.setCommunication_rating(requestDto.getCommunication_rating());
        requestDto.setCommunication_desc(requestDto.getCommunication_desc());
        requestDto.setPerformance_rating(requestDto.getPerformance_rating());
        requestDto.setPerformance_desc(requestDto.getPerformance_desc());
        requestDto.setClientInfo(client); // 발주처 정보

        EvaluationRequestEntity newEval = requestDto.toEntity();
        newEval = evaluationRequestDao.save(newEval);

        Map response = new HashMap<String, Object>();
        response.put("message", "프로젝트에 고객 평가가 등록되었습니다.");
        response.put("status", 1);
        response.put("cus_eval_id", newEval.getCusEvalId());
        response.put("client_id", newEval.getClientInfoEntity().getClientId());

        return response;
    }

    @Transactional(readOnly = true)
    // 동료평가 조회
    public ParticipantEvaluationResponseDto coworkEvalPersonalRead(Long addId) {
        Optional<AccountEntity> accountEntity = accountDao.findByaccId(addId);
        AccountEntity account = accountEntity.get();
        Long empId = account.getEmployeeEntity().getEmpId();
        EmployeeEntity emp = employeeDao.findByEmpId(empId);

        // 프로젝트별 받은 평가 조회
        return ParticipantEvaluationResponseDto.from(emp);
    }

//    @Transactional(readOnly = true)
//    // 고객평가 조회 (고객이 조회)
//    public ClientEvaluationResponseDto clientEvalRead(Long addId) {
//        // 고객 ID (고객 사번)
//        Optional<AccountEntity> accountEntity = accountDao.findByaccId(addId);
//        AccountEntity account = accountEntity.get();
//        String clientName = account.getEmployeeEntity().getEmpName();
//
//        // 고객 정보
//        ClientInfoEntity client = clientInfoDao.findByClientEmpName(clientName);
////        if (client == null) {
////            Map response = new HashMap<String, Object>();
////            response.put("message", "관련 고객 정보가 없습니다.");
////            response.put("status", 0);
////            response.put("clientName", clientName);
////
////            return response;
////        }
//
//        // 프로젝트별 받은 고객 평가 조회
//        return ClientEvaluationResponseDto.from(client);
//    }
}
