package com.dbproj.manageprompt.controller;

import com.dbproj.manageprompt.entity.EmployeeEntity;
import com.dbproj.manageprompt.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    // 직원 정보 조회 (개인)
    //@GetMapping("/info/read")

    // 직원 검색
    // 직무, 프로젝트 이름, 스킬이름, 프로젝트 참여 여부로 직원을 검색
    //@GetMapping("/list")
    //public EmployeeResponseDto findOne(@RequestParam) {
    //    return employeeService.
    //}

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
//    @GetMapping("/info/update")
}
