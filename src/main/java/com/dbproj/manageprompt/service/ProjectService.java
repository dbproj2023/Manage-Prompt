package com.dbproj.manageprompt.service;

import com.dbproj.manageprompt.dao.ProjectDao;
import com.dbproj.manageprompt.dto.ProjectDetailResponseDto;
import com.dbproj.manageprompt.entity.ProjectEntity;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectDao projectDao;

    // 프로젝트 정보 & 프로젝트 참여 직원
    @Transactional(readOnly = true)
    public ProjectDetailResponseDto findOne(long proId) {
        ProjectEntity post = findProject(proId);
        return ProjectDetailResponseDto.from(post);
    }

    @Transactional(readOnly = true)
    public ProjectEntity findProject(long proId) {
        ProjectEntity project = projectDao.findById(proId).orElseThrow(() ->
                new IllegalArgumentException("해당 게시물이 존재하지 않습니다. => " + proId));
        return project;
    }
}
