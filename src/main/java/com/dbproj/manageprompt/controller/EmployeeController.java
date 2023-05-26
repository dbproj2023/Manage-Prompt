package com.dbproj.manageprompt.controller;

import com.dbproj.manageprompt.Interface.WapperInterface;
import com.dbproj.manageprompt.dao.AccountDao;
import com.dbproj.manageprompt.dto.*;
import com.dbproj.manageprompt.entity.AccountEntity;
import com.dbproj.manageprompt.entity.EmployeeEntity;
import com.dbproj.manageprompt.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AccountDao accountDao;

    //직원 정보 조회 (개인)
    @GetMapping("/info/read")
    public EmployeeRequestDto readFrom(HttpSession session, Model model) {
        Long accid = (Long) session.getAttribute("AccId");
        Optional<AccountEntity> accountEntity = accountDao.findByaccId(accid);
        AccountEntity account = accountEntity.get();
        EmployeeEntity employee =  account.getEmployeeEntity();
        EmployeeRequestDto empDto = EmployeeRequestDto.toDto(employee);
        model.addAttribute("employee", empDto);
        return empDto;
    }

    // 직원 검색
    // 직무, 프로젝트 이름, 스킬이름, 프로젝트 참여 여부로 직원을 검색
    @GetMapping("/list")
    public List<WapperInterface> getProjEmployeeList(ProjectEmployeeSearchDto projectEmployeeSearchDto) {
        return employeeService.getProjectEmployeeSearch(projectEmployeeSearchDto);
    }


    // 직원 정보 조회 (프로젝트 직원 추가용 검색)
    @GetMapping("/search")
    public List<EmployeeEntity> getEmployeeList(
            @RequestParam("emp_id") String empId,
            @RequestParam("emp_name") String empName,
            @RequestParam("emp_skill") String empSkill
    ){
        log.info(empName);
        return employeeService.getEmployeeSearch(empId,empName,empSkill);
    }
    // keyword: 조회할 사번, 이름, 스킬

    // 직원 정보 수정
    @PatchMapping("/info/update")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map update(HttpSession session, EmployeeUpdateRequestDto requestDto) {
        Long accid = (Long) session.getAttribute("AccId");
        Optional<AccountEntity> accountEntity = accountDao.findByaccId(accid);
        AccountEntity account = accountEntity.get();
        Long empId =  account.getEmployeeEntity().getEmpId();
        Map response = employeeService.update(empId, requestDto);
        return response;
    }

    // 직원별 참여 프로젝트 조회 (개인)
    @GetMapping("/search/proj/list")
    @ResponseStatus(HttpStatus.OK)
    public ProjectEmployeeResponseDto participantProjectAllRead(HttpSession session) {
        Long accId = (Long) session.getAttribute("AccId");
        return employeeService.participantProjectAllRead(accId);
    }

}
