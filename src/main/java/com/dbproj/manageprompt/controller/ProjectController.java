package com.dbproj.manageprompt.controller;

import com.dbproj.manageprompt.dto.*;
import com.dbproj.manageprompt.service.ProjectService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/proj")
public class ProjectController {

    private final ProjectService projectService;

    // 프로젝트 조회 및 검색
    @GetMapping("/lists")
    public List<ProjectResponseDto> findAllPageable(
            @RequestParam(value = "year", defaultValue="0") Integer year,
            @RequestParam(value = "period", required = false) String period,
            @RequestParam(value = "state", defaultValue="0") Integer state,
            @RequestParam(value = "pro_name", required = false) String pro_name,
            @RequestParam(value = "client_name", required = false) String client_name,
            @RequestParam(value = "budge_start", defaultValue="0") Integer budge_start,
            @RequestParam(value = "budge_end", defaultValue="0") Integer budge_end,
            @PageableDefault(size = 30, direction = Sort.Direction.DESC) Pageable pageable) {
        // 전체 게시물 조회 (page, size)
        if (period == null) {
            return projectService.findAll(pageable)
                    .stream()
                    .map(ProjectResponseDto::from)
                    .collect(Collectors.toList());
        } else {
            // 프로젝트 관리 (프로젝트 다중 검색)
            return projectService.findAll(pageable)
                    .stream()
                    .map(ProjectResponseDto::from)
                    .collect(Collectors.toList());
        }
    }

    // 프로젝트 정보 & 프로젝트 참여 직원
    @GetMapping("/{pro_id}")
    public ProjectDetailResponseDto findOne(@PathVariable(value = "pro_id") String proId) {
        return projectService.findOne(proId);
    }

    // 프로젝트 및 발주처 등록
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
//    public IdStringResponseDto create(@RequestBody @Valid ProjectAndClientCreateRequestDto requestDto) {
    public IdStringResponseDto create(ProjectAndClientCreateRequestDto requestDto) {
//        long responseId = projectService.create(sessionDto.getId, requestDto); // 로그인 구현 후 변경해야함.
        String responseId = projectService.create(requestDto);
        return new IdStringResponseDto(responseId);
    }

    // 프로젝트 수정
    @PatchMapping("/update/{pro_id}")
    @ResponseStatus(HttpStatus.OK)
//    public IdStringResponseDto update(@PathVariable(value = "pro_id") String proId, @RequestBody @Valid ProjectUpdateRequestDto requestDto) {
    public IdStringResponseDto update(@PathVariable(value = "pro_id") String proId, ProjectUpdateRequestDto requestDto) {
        String responseId = projectService.update(proId, requestDto);
        return new IdStringResponseDto(responseId);
    }
}
