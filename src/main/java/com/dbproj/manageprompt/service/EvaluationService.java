package com.dbproj.manageprompt.service;

import com.dbproj.manageprompt.dao.AccountDao;
import com.dbproj.manageprompt.dao.EmployeeProjectDao;
import com.dbproj.manageprompt.dao.EvaluationInnerDao;
import com.dbproj.manageprompt.dao.EvaluationRequestDao;
import com.dbproj.manageprompt.dto.ParticipantEvaluationCreateRequestDto;
import com.dbproj.manageprompt.entity.AccountEntity;
import com.dbproj.manageprompt.entity.EmployeeProjectEntity;
import com.dbproj.manageprompt.entity.EvaluationInnerEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j

public class EvaluationService {

    private final EvaluationInnerDao evaluationInnerDao;
    private final EvaluationRequestDao evaluationRequestDao;
    private final EmployeeProjectDao employeeProjectDao;
    private final AccountDao accountDao;


    // 동료 평가 등록
    public Map coworkEvalcreate(Long addId, ParticipantEvaluationCreateRequestDto requestDto) {
        Optional<AccountEntity> accountEntity = accountDao.findByaccId(addId);
        AccountEntity account = accountEntity.get();
        Long empId =  account.getEmployeeEntity().getEmpId();

        // 피평가자 직원의 직원프로젝트 조회
        EmployeeProjectEntity empProj = employeeProjectDao.
                findByProjectEntity_ProNameAndAndEmployeeEntity_EmpId(
                        requestDto.getPro_name(),
                        requestDto.getCoworker_emp_id()
                );

        if (empProj == null) {
            Map response = new HashMap<String, Object>();
            response.put("message", "관련 정보가 없습니다.");
            response.put("status", 1);

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

}
