package com.dbproj.manageprompt.controller;

import com.dbproj.manageprompt.dto.ClientEvaluationCreateRequestDto;
import com.dbproj.manageprompt.dto.ParticipantEvaluationCreateRequestDto;
import com.dbproj.manageprompt.dto.ParticipantEvaluationResponseInterface;

import com.dbproj.manageprompt.service.EvaluationService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/evaluation")
public class EvaluationController {

    private final EvaluationService evaluationService;

    // PM/동료 평가 등록
    @PostMapping("/coworker/create")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map coworkEvalcreate(HttpSession session, ParticipantEvaluationCreateRequestDto requestDto) {
        Long accId = (Long) session.getAttribute("AccId");
        Map response = evaluationService.coworkEvalcreate(accId, requestDto);
        return response;
    }

    // 고객 평가 등록 (수정 필요)
    @PostMapping("/client/create")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map clientEvalcreate(HttpSession session, ClientEvaluationCreateRequestDto requestDto) {
        Long accId = (Long) session.getAttribute("AccId");
        Map response = evaluationService.clientEvalcreate(accId, requestDto);
        return response;
    }

    // 개인 동료 평가 조회 (로그인 시)
    @GetMapping("/coworker/read")
    @ResponseStatus(HttpStatus.OK)
    public Map coworkEvalPersonalRead(HttpSession session) {
        Long accId = (Long) session.getAttribute("AccId");
        return evaluationService.coworkEvalPersonalRead(accId);
    }

    // 직원별 평가 조회
    @GetMapping("/coworker/read/{emp_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipantEvaluationResponseInterface>  coworkEvalEmployeeRead(@PathVariable(value = "emp_id") Long empId) {
        return evaluationService.coworkEvalEmployeeRead(empId);
    }

    // 고객이 발주한 프로젝트 조회(평가페이지에서 프로젝트 정보 조회용)
    @GetMapping("/client/project")
    @ResponseStatus(HttpStatus.OK)
    public Map getClientProject(HttpSession session) {
        Long accId = (Long) session.getAttribute("AccId");
        Map response = evaluationService.getClientProject(accId);
        return response;
    }
}
