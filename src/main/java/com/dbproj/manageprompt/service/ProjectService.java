package com.dbproj.manageprompt.service;

import com.dbproj.manageprompt.common.exception.NotFoundException;
import com.dbproj.manageprompt.dao.*;
import com.dbproj.manageprompt.dto.*;
import com.dbproj.manageprompt.entity.*;

import com.dbproj.manageprompt.specification.ProjectSpecification;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectDao projectDao;
    private final ClientInfoDao clientInfoDao;

    private final EmployeeProjectDao employeeProjectDao;

    private final EmployeeDao employeeDao;

    private final RoleDao roleDao;

    // 전체 프로젝트 조회
    @Transactional(readOnly = true)
    public Page<ProjectEntity> findAll(Pageable pageable) {
        return projectDao.findAll(pageable);
    }

    // 프로젝트 검색
    public List<ProjectSpecificationResponseDto> search(Integer year, String period, Integer state, String pro_name, String client_name, Integer budge_start, Integer budge_end){
        Specification<ProjectEntity> spec = (root, query, criteriaBuilder) -> null;

//        if (year != null) spec = spec.and(ProjectSpecification.equalYear(year));
//        if (period != null) spec = spec.and(ProjectSpecification.equalYear(year));
//        if (state != null) spec = spec.and(ProjectSpecification.equalYear(year));
        if (pro_name != null) spec = spec.and(ProjectSpecification.equalProName(pro_name));
        if (client_name != null) spec = spec.and(ProjectSpecification.equalClientName(client_name));
        if (budge_start != null & budge_end != null) spec = spec.and(ProjectSpecification.betweenBudget(budge_start, budge_end));

        return projectDao.findAll(spec).stream().map(ProjectSpecificationResponseDto::from).collect(Collectors.toList());
    }

    // 프로젝트 정보 & 프로젝트 참여 직원 모두 조회
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

    // 프로젝트 참여 직원 등록
    public Long employeeAdd(ProjectAddEmployeeRequestDto addEmpRequestDto) {
        // EmployeeProject Entity search
        EmployeeEntity employee = employeeDao.findById(addEmpRequestDto.getEmp_id()).orElseThrow(() ->
                new IllegalArgumentException("해당 사번의 직원은 존재하지 않습니다. => " + addEmpRequestDto.getEmp_id()));

        // Project Entity search
        ProjectEntity project = projectDao.findByProName(addEmpRequestDto.getPro_name());

        // Role Entity search
        RoleEntity role = roleDao.findByRoleId(addEmpRequestDto.getRole_id());

        // EmployeeProject Entity save
        addEmpRequestDto.setStart_date(addEmpRequestDto.getStart_date());
        addEmpRequestDto.setEnd_date(addEmpRequestDto.getEnd_date());
        addEmpRequestDto.setEmployee(employee);
        addEmpRequestDto.setProject(project);
        addEmpRequestDto.setRole(role);

        EmployeeProjectEntity empProj = addEmpRequestDto.toEntity();
        empProj = employeeProjectDao.save(empProj);

        return empProj.getEmpProId();
    }
    // 프로젝트별 참여 직원 전체 조회


    // 프로젝트 참여 직원 수정
    public Long employeeUpdate(ProjectEmployeeUpdateRequestDto requestDto) {
        EmployeeProjectEntity empProj = employeeProjectDao.
                findByProjectEntity_ProNameAndAndEmployeeEntity_EmpId(
                        requestDto.getPro_name(),
                        requestDto.getEmp_id()
                );
        RoleEntity role = roleDao.findByRoleId(requestDto.getRole_id());
        empProj.update(
                requestDto.getStart_date(),
                requestDto.getEnd_date(),
                role
        );
        return employeeProjectDao.save(empProj).getEmpProId();
    }
}
