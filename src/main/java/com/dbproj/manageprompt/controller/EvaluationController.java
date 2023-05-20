package com.dbproj.manageprompt.controller;

import com.dbproj.manageprompt.dto.ParticipantEvaluationCreateRequestDto;
import com.dbproj.manageprompt.service.EvaluationService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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

    // 고객 평가 등록
    @PostMapping("/client/create")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map clientEvalcreate(HttpSession session, ParticipantEvaluationCreateRequestDto requestDto) {
        Long accId = (Long) session.getAttribute("AccId");
        Map response = evaluationService.coworkEvalcreate(accId, requestDto);
        return response;
    }

}
