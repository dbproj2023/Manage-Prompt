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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectDao projectDao;
    private final ClientInfoDao clientInfoDao;

    private final EmployeeProjectDao employeeProjectDao;

    private final EmployeeDao employeeDao;

    private final RoleDao roleDao;

    // 프로젝트 등록 시 프로젝트 아이디 중복 확인
    @Transactional(readOnly = true)
    public Integer checkDuplicateProId(CheckProjectIdRequestDto requestDto) {
        ProjectEntity project = projectDao.findByProId(requestDto.getProId());
        if (project == null) {
            return 0; // 해당 아이디의 프로젝트 없음
        }
        return 1; // 해당 아이디의 프로젝트 존재
    }

    // 전체 프로젝트 조회
    @Transactional(readOnly = true)
    public Page<ProjectEntity> findAll(Pageable pageable) {
        return projectDao.findAll(pageable);
    }

    // 프로젝트 검색
    public List<ProjectSpecificationResponseDto> search(Date start_date, Date end_date, String pro_name, String client_name, Integer budge_start, Integer budge_end) throws ParseException {
        Specification<ProjectEntity> spec = (root, query, criteriaBuilder) -> null;
        
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = now.format(formatter);
        Date localDate = formatter2.parse(formatted);

        SimpleDateFormat recvSimpleFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        SimpleDateFormat tranSimpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

        if (start_date != null && end_date != null) {
            Date start_data = recvSimpleFormat.parse(String.valueOf(start_date));
            String start_date_format = tranSimpleFormat.format(start_data);
            Date start_date_foramtted = tranSimpleFormat.parse(start_date_format);

            Date end_data = recvSimpleFormat.parse(String.valueOf(end_date));
            String end_date_format = tranSimpleFormat.format(end_data);
            Date end_date_foramtted = tranSimpleFormat.parse(end_date_format);

//            spec = spec.and(ProjectSpecification.betweenDate(start_date_foramtted, end_date_foramtted));
            spec = spec.and(ProjectSpecification.searchStartDate(start_date_foramtted));
            spec = spec.and(ProjectSpecification.searchEndDate(end_date_foramtted));
        }

        if (start_date != null && end_date == null) {
            Date start_data = recvSimpleFormat.parse(String.valueOf(start_date));
            String start_date_format = tranSimpleFormat.format(start_data);
            Date start_date_foramtted = tranSimpleFormat.parse(start_date_format);

            System.out.println(start_date_foramtted);
            spec = spec.and(ProjectSpecification.searchStartDate(start_date_foramtted));
        }
        if (start_date == null && end_date != null) {
            Date end_data = recvSimpleFormat.parse(String.valueOf(end_date));
            String end_date_format = tranSimpleFormat.format(end_data);
            Date end_date_foramtted = tranSimpleFormat.parse(end_date_format);

            spec = spec.and(ProjectSpecification.searchEndDate(end_date_foramtted));
        }

        if (pro_name != null && !pro_name.isBlank()) spec = spec.and(ProjectSpecification.equalProName(pro_name));
        if (client_name != null && !client_name.isBlank()) spec = spec.and(ProjectSpecification.equalClientName(client_name));

        if (budge_start != 0 && budge_end != 0) spec = spec.and(ProjectSpecification.betweenBudget(budge_start, budge_end));
        if (budge_start != 0 && budge_end == 0) spec = spec.and(ProjectSpecification.upperBudget(budge_start));
        if (budge_start == 0 && budge_end != 0) spec = spec.and(ProjectSpecification.lowerBudget(budge_end));

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
    public Map create(ProjectAndClientCreateRequestDto projCreateDto) {
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

        Map response = new HashMap<String, Object>();
        response.put("message", "프로젝트와 발주처가 추가되었습니다.");
        response.put("status", 1);
        response.put("pro_id", newProject.getProId());
        response.put("cliend_id", newClient.getClientId());

        return response;
    }

    // 프로젝트 수정
    public Map update(String proId, ProjectUpdateRequestDto requestDto) {
        ProjectEntity updateProject = projectDao.findById(proId).orElseThrow(NotFoundException::new);
        System.out.println(requestDto.getStart_date());
        updateProject.update(
                requestDto.getStart_date(),
                requestDto.getEnd_date(),
                requestDto.getBudget()
        );

        Map response = new HashMap<String, Object>();
        response.put("message", "프로젝트가 수정되었습니다.");
        response.put("status", 1);
        response.put("pro_id", projectDao.save(updateProject).getProId());

        return response;
    }

    // 프로젝트 참여 직원 등록
    public Map employeeAdd(ProjectAddEmployeeRequestDto addEmpRequestDto) {
        EmployeeProjectEntity isEmpProj = employeeProjectDao.
                findByProjectEntity_ProIdAndAndEmployeeEntity_EmpId(
                        addEmpRequestDto.getPro_id(),
                        addEmpRequestDto.getEmp_id()
                );

        if (isEmpProj == null) {
            // EmployeeProject Entity search
            EmployeeEntity employee = employeeDao.findById(addEmpRequestDto.getEmp_id()).orElseThrow(() ->
                    new IllegalArgumentException("해당 사번의 직원은 존재하지 않습니다. => " + addEmpRequestDto.getEmp_id()));

            // Project Entity search
            ProjectEntity project = projectDao.findByProId(addEmpRequestDto.getPro_id());

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

            Map response = new HashMap<String, Object>();
            response.put("message", "프로젝트에 직원이 추가되었습니다.");
            response.put("status", 1);
            response.put("emp_pro_id", empProj.getEmpProId());

            return response;
        }

        Map response = new HashMap<String, Object>();
        response.put("message", "이미 프로젝트에 추가된 직원입니다.");
        response.put("status", 0);
        response.put("emp_pro_id", isEmpProj.getEmpProId());
        response.put("_pro_id", addEmpRequestDto.getPro_id());
        response.put("emp_id", addEmpRequestDto.getEmp_id());

        return response;
    }
    // 프로젝트별 참여 직원 전체 조회


    // 프로젝트 참여 직원 수정
    public Map employeeUpdate(ProjectEmployeeUpdateRequestDto requestDto) {
        EmployeeProjectEntity empProj = employeeProjectDao.
                findByProjectEntity_ProIdAndAndEmployeeEntity_EmpId(
                        requestDto.getPro_id(),
                        requestDto.getEmp_id()
                );

        if (empProj == null) {
            Map response = new HashMap<String, Object>();
            response.put("message", "존재하지 않는 직원이거나 프로젝트입니다.");
            response.put("status", 0);

            return response;
        }

        RoleEntity role = roleDao.findByRoleId(requestDto.getRole_id());

        empProj.update(
                requestDto.getStart_date(),
                requestDto.getEnd_date(),
                role
        );

        Map response = new HashMap<String, Object>();
        response.put("message", "프로젝트에 직원이 수정되었습니다.");
        response.put("status", 1);
        response.put("emp_pro_id",employeeProjectDao.save(empProj).getEmpProId());

        return response;
    }
}
