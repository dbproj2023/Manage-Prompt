package com.dbproj.manageprompt.controller;

import com.dbproj.manageprompt.dao.AccountDao;
import com.dbproj.manageprompt.dto.*;
import com.dbproj.manageprompt.entity.AccessInfoEntity;
import com.dbproj.manageprompt.entity.AccountEntity;
import com.dbproj.manageprompt.service.AccountService;
import com.dbproj.manageprompt.service.EmailAuthService;

import com.dbproj.manageprompt.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@Slf4j
public class AccountController {

    private final AccountService accountService;

    private final EmployeeService employeeService;

    private final EmailAuthService emailAuthService;

    //신규 유저 등록(관리자)
    @PostMapping("/new-user")
    @ResponseStatus(HttpStatus.OK)
    public IdResponseDto create(AccountCreateRequestDto requestDto) {
        Long responseId = accountService.create(requestDto);
        return new IdResponseDto(responseId);
    }

    //신규 유저 등록(직원)
    //관리자가 초기 설정한 계정으로 로그인하면 auth_id session으로 가져옴
    @GetMapping("/user")
    public String updateFrom(HttpSession session, Model model) {
        String myauthid = (String) session.getAttribute("AuthId");
        AccountRequestDto memberDTO = accountService.updateForm(myauthid);
        model.addAttribute("updateMember", memberDTO);
        log.info(myauthid);
        return "user";
    }
    //등록 처리
    @PostMapping("/user")
    public String updateUser(HttpSession session,@ModelAttribute AccountCreateRequestDto memberDTO) {
        Long accid = (Long) session.getAttribute("AccId");
        memberDTO.setAcc_id(accid);
        memberDTO.setCreated_at(LocalDateTime.now());
        accountService.updateUser(memberDTO);
        return "success";
    }


    //로그인
    @PostMapping("/login")
    public AccountRequestDto login(@ModelAttribute AccountRequestDto memberDto, HttpSession session) {
        AccountRequestDto loginResult = accountService.login(memberDto);
        if (loginResult != null) {
            //로그인 성공
            session.setAttribute("AccId", loginResult.getAccId());
            session.setAttribute("AuthId", loginResult.getAuthId());
            session.setAttribute("accessGrade", loginResult.getAccessGrade());
            return loginResult;
        } else {
            //로그인 실패
            return null;
        }
    }

    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "logout";
    }
    @PostMapping("/checkEMPID")
    public ResponseEntity<Boolean> checkEmpIdDuplicate(Long emp_id) {
        return ResponseEntity.ok(employeeService.checkEmpIdDuplicate(emp_id));
    }
    @PostMapping("/checkEmail")
    public ResponseEntity<Boolean> checkEmpEmailDuplicate(String emp_email) {
        return ResponseEntity.ok(employeeService.checkEmpEmailDuplicate(emp_email));
    }
    @PostMapping("/checkID")
    public ResponseEntity<Boolean> checkIdDuplicate(String auth_id) {
        return ResponseEntity.ok(accountService.checkAuthIdDuplicate(auth_id));
    }

    // 이메일 발송
    @PostMapping(value = "/help/sendEmail")
    public Map sendEmail(@Valid EmailAuthDto emailAuthDto) throws JSONException {
        log.info("email " + emailAuthDto.getEmail() );
        Map response = emailAuthService.sendEmail(emailAuthDto);
        return response;
    }

    // 인증코드 검증
    @PostMapping(value = "/help/verifyEmail")
    public Map verifyEmail(EmailAuthDto emailAuthDto) {
        Map response = emailAuthService.verifyEmail(emailAuthDto);
        return response;
    }
    //비밀번호 변경
    @PatchMapping("/help/resetPW")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map resetPw(HttpSession httpSession,@ModelAttribute AccountPwUpdateRequestDto accountPwUpdateRequestDto) {
        Long accid = (Long) httpSession.getAttribute("AccId");
        log.info(accountPwUpdateRequestDto.getOld_pw());
        Map response = accountService.updatePw(accid, accountPwUpdateRequestDto);
        return response;
    }

    //권한 수정
    @PatchMapping("/role/update")
    @ResponseStatus(HttpStatus.OK)
    public Map roleUpdate(@ModelAttribute AccessUpdateRequestDto updateDto) {
        log.info(String.valueOf(updateDto.getEmp_id()));
        log.info(updateDto.getEmp_name());
        Map response = accountService.roleUpdate(updateDto);
        return response;
    }

    // 권한 미부여(9번) 직원 조회
    @GetMapping("/role/unrecognized")
    @ResponseStatus(HttpStatus.OK)
    public List<RoleNonAccessResponseInterface> roleUnrecognized() {
        List<RoleNonAccessResponseInterface> response = accountService.findAllNonAccessEmp();
        return response;
    }
}
