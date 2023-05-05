package com.dbproj.manageprompt.controller;

import com.dbproj.manageprompt.dto.ProjectDetailResponseDto;
import com.dbproj.manageprompt.service.ProjectService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/proj")
public class ProjectController {

    private final ProjectService projectService;

    // 프로젝트 및 발주처 추가

    // 프로젝트 수정

    // 프로젝트 관리 (프로젝트 다중 검색)


    // 프로젝트 정보 & 프로젝트 참여 직원
    @GetMapping("/{pro_id}")
    public ProjectDetailResponseDto findOne(@PathVariable long pro_id) {
        return projectService.findOne(pro_id);
    }
}
