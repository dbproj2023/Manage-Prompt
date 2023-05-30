package com.dbproj.manageprompt.controller;

import com.dbproj.manageprompt.Interface.ProjectSearchResponseInterface;
import com.dbproj.manageprompt.dto.*;
import com.dbproj.manageprompt.entity.ProjectEntity;
import com.dbproj.manageprompt.service.ProjectService;

import com.dbproj.manageprompt.specification.ProjectSpecification;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/proj")
public class ProjectController {

    private final ProjectService projectService;

    // 프로젝트 조회
    @GetMapping("/lists")
    public List<ProjectResponseDto> findAllPageable(
            @PageableDefault(size = 30, direction = Sort.Direction.DESC) Pageable pageable) {
        return projectService.findAll(pageable)
                .stream()
                .map(ProjectResponseDto::from)
                .collect(Collectors.toList());
    }

     // 프로젝트 검색
    @GetMapping("/lists/search")
    public List<ProjectSearchResponseInterface> search(
            @RequestParam(value = "period_start", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
            @RequestParam(value = "period_end", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate,
            @RequestParam(value = "pro_name", required = false, defaultValue="") String proName,
            @RequestParam(value = "client_name", required = false, defaultValue="") String clientName,
            @RequestParam(value = "budge_start", defaultValue="0") Integer budgeStart,
            @RequestParam(value = "budge_end", defaultValue="0") Integer budgeEnd,
            @PageableDefault(size = 30, direction = Sort.Direction.DESC) Pageable pageable) throws ParseException {
       return projectService.search(startDate,  endDate, proName, clientName, budgeStart, budgeEnd);
    }

    // 년도별 프로젝트 수행 횟수 및 총 발주 금액
    @GetMapping("/lists/search/summary")
    public ProjectAggNumBudgetByYearResponseInterface search(
            @RequestParam(value = "year", defaultValue="0") Integer year) {
        return projectService.findSumNumAndBudgetByYear(year);
    }

    // 프로젝트 정보 & 프로젝트 참여 직원 & 평가정보
    @GetMapping("/{pro_id}")
    public ProjectDetailResponseDto findOne(@PathVariable(value = "pro_id") String proId) {
        return projectService.findOne(proId);
    }

    // 프로젝트 및 발주처 등록
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
//    public IdStringResponseDto create(@RequestBody @Valid ProjectAndClientCreateRequestDto requestDto) {
    public Map create(ProjectAndClientCreateRequestDto requestDto) {
//        long responseId = projectService.create(sessionDto.getId, requestDto); // 로그인 구현 후 변경해야함.
        Map response = projectService.create(requestDto);
        return response;
    }

    // 프로젝트 수정
    @PatchMapping("/update/{pro_id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
//    public IdStringResponseDto update(@PathVariable(value = "pro_id") String proId, @RequestBody @Valid ProjectUpdateRequestDto requestDto) {
    public Map update(@PathVariable(value = "pro_id") String proId, ProjectUpdateRequestDto requestDto) {
        Map response = projectService.update(proId, requestDto);
        return response;
    }

    // 프로젝트 참여 직원 등록
    @PostMapping("/member/add")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
//    public IdResponseDto create(@RequestBody @Valid ProjectAddEmployeeRequestDto requestDto) {
    public Map create(ProjectAddEmployeeRequestDto requestDto) {
//        long responseId = projectService.employeeAdd(sessionDto.getId, requestDto); // 로그인 구현 후 변경해야함.
        Map response = projectService.employeeAdd(requestDto);
        return response;
    }

    // 프로젝트 참여 직원 수정
    @PatchMapping("/member/update")
    @ResponseStatus(HttpStatus.OK)
//    public IdResponseDto create(@RequestBody @Valid ProjectEmployeeUpdateRequestDto requestDto) {
    public Map update(ProjectEmployeeUpdateRequestDto requestDto) {
//        long responseId = projectService.employeeUpdate(sessionDto.getId, requestDto); // 로그인 구현 후 변경해야함.
        Map response = projectService.employeeUpdate(requestDto);
        return response;
    }

    // 프로젝트 아이디 중복 확인
    @PostMapping("/checkProid")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map checkDuplicateProId(CheckProjectIdRequestDto requestDto) {
        Map response = new HashMap<String, Object>();
        Integer responseId = projectService.checkDuplicateProId(requestDto);
        if (responseId == 0) {
            response.put("message", "사용 가능한 아이디입니다.");
            response.put("value", 1);
        }
        if (responseId == 1){
            response.put("message", "사용 불가능한 아이디입니다.");
            response.put("value", 0);
        }
        return response;
    }

    // 프로젝트별 평가 조회

}
