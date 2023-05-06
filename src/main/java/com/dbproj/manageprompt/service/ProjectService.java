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

    // 프로젝트 검색
//    public List<ProjectDetailResponseDto> Search(Integer year, String period, Integer state, String pro_name, String client_name, Integer budge_start, Integer budge_end){
//    }

    // 프로젝트 정보 & 프로젝트 참여 직원
    @Transactional(readOnly = true)
    public ProjectDetailResponseDto findOne(String proId) {
        ProjectEntity project = findProject(proId);
        return ProjectDetailResponseDto.from(project);
    }

    // findProject method
    @Transactional(readOnly = true)
    public ProjectEntity findProject(String proId) {
        ProjectEntity project = projectDao.findById(proId).orElseThrow(() ->
                new IllegalArgumentException("해당 프로젝트는 존재하지 않습니다. => " + proId));
        return project;
    }

    // 프로젝트 및 발주처 등록
    public String create(ProjectAndClientCreateRequestDto projCreateDto) {
        // 발주처 추가
        ClientRequestDto clientRequestDto = new ClientRequestDto();
        clientRequestDto.setClientName(projCreateDto.getClient_name());
        clientRequestDto.setClientEmpName(projCreateDto.getClient_emp_name());
        clientRequestDto.setClientEmpPh(projCreateDto.getClient_emp_ph());
        clientRequestDto.setClientEmpEmail(projCreateDto.getClient_emp_email());

        ClientInfoEntity newClient = clientRequestDto.toEntity();
        clientInfoDao.save(newClient);

        // 프로젝트 추가
        projCreateDto.setPro_id(projCreateDto.getPro_id());
        projCreateDto.setPro_name(projCreateDto.getPro_name());
        projCreateDto.setStart_date(projCreateDto.getStart_date());
        projCreateDto.setEnd_date(projCreateDto.getEnd_date());
        projCreateDto.setBudget(projCreateDto.getBudget());
        projCreateDto.setClient(newClient);

        ProjectEntity newProject = projCreateDto.toEntity();
        newProject = projectDao.save(newProject);

        return newProject.getProId();
    }

    // 프로젝트 수정
    public String update(String proId, ProjectUpdateRequestDto requestDto) {
        ProjectEntity updateProject = projectDao.findById(proId).orElseThrow(NotFoundException::new);
        System.out.println(requestDto.getStart_date());
        updateProject.update(
                requestDto.getStart_date(),
                requestDto.getEnd_date(),
                requestDto.getBudget()
        );

        return projectDao.save(updateProject).getProId();
    }
}
