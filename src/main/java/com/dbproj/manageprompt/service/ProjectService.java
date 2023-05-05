package com.dbproj.manageprompt.service;

import com.dbproj.manageprompt.common.exception.NotFoundException;
import com.dbproj.manageprompt.dao.ClientInfoDao;
import com.dbproj.manageprompt.dao.ProjectDao;
import com.dbproj.manageprompt.dto.ClientRequestDto;
import com.dbproj.manageprompt.dto.ProjectAndClientCreateRequestDto;
import com.dbproj.manageprompt.dto.ProjectDetailResponseDto;
import com.dbproj.manageprompt.dto.ProjectUpdateRequestDto;
import com.dbproj.manageprompt.entity.ClientInfoEntity;
import com.dbproj.manageprompt.entity.ProjectEntity;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectDao projectDao;
    private final ClientInfoDao clientInfoDao;

    // 전체 프로젝트 조회
    @Transactional(readOnly = true)
    public Page<ProjectEntity> findAll(Pageable pageable) {
        return projectDao.findAll(pageable);
    }

    // 프로젝트 정보 & 프로젝트 참여 직원
    @Transactional(readOnly = true)
    public ProjectDetailResponseDto findOne(long proId) {
        ProjectEntity project = findProject(proId);
        return ProjectDetailResponseDto.from(project);
    }

    // findProject method
    @Transactional(readOnly = true)
    public ProjectEntity findProject(long proId) {
        ProjectEntity project = projectDao.findById(proId).orElseThrow(() ->
                new IllegalArgumentException("해당 프로젝트는 존재하지 않습니다. => " + proId));
        return project;
    }

    // 프로젝트 및 발주처 등록
    public long create(ProjectAndClientCreateRequestDto projCreateDto) {
        // 발주처 추가
        ClientRequestDto clientRequestDto = new ClientRequestDto();
        clientRequestDto.setClientName(projCreateDto.getClientName());
        clientRequestDto.setClientEmpName(projCreateDto.getClientEmpName());
        clientRequestDto.setClientEmpPh(projCreateDto.getClientEmpPh());
        clientRequestDto.setClientEmpEmail(projCreateDto.getClientEmpEmail());

        ClientInfoEntity newClient = clientRequestDto.toEntity();
        clientInfoDao.save(newClient);

        // 프로젝트 추가
        projCreateDto.setProName(projCreateDto.getProName());
        projCreateDto.setStartDate(projCreateDto.getStartDate());
        projCreateDto.setEndDate(projCreateDto.getEndDate());
        projCreateDto.setBudget(projCreateDto.getBudget());
        projCreateDto.setClient(newClient);

        ProjectEntity newProject = projCreateDto.toEntity();
        newProject = projectDao.save(newProject);

        return newProject.getProId();
    }

    // 프로젝트 수정
    public long update(long proId, ProjectUpdateRequestDto requestDto) {
        ProjectEntity updateProject = projectDao.findById(proId).orElseThrow(NotFoundException::new);
        updateProject.update(
                requestDto.getStartDate(),
                requestDto.getEndDate(),
                requestDto.getBudget()
        );

        return projectDao.save(updateProject).getProId();
    }
}
