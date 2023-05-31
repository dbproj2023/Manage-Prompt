package com.dbproj.manageprompt.controller;

import com.dbproj.manageprompt.dao.AccountDao;
import com.dbproj.manageprompt.dao.EmployeeDao;
import com.dbproj.manageprompt.dto.*;
import com.dbproj.manageprompt.entity.AccessInfoEntity;
import com.dbproj.manageprompt.entity.AccountEntity;
import com.dbproj.manageprompt.entity.EmployeeEntity;
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
import java.util.HashMap;
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

    private final AccountDao accountDao;

    private final EmployeeDao employeeDao;

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
    @PatchMapping("/user")
    public String updateUser(HttpSession session,@ModelAttribute AccountCreateRequestDto memberDTO) {
        Long accid = (Long) session.getAttribute("AccId");
        memberDTO.setAcc_id(accid);
        memberDTO.setCreated_at(LocalDateTime.now());
        accountService.updateUser(memberDTO);
        return "success";
    }


    //로그인
    @PostMapping("/login")
    public Map login(@ModelAttribute AccountRequestDto memberDto, HttpSession session) {
        AccountRequestDto loginResult = accountService.login(memberDto);
        if (loginResult != null) {
            // 계정 조회
            Optional<AccountEntity> accountEntity = accountDao.findByaccId(loginResult.getAccId());
            AccountEntity account = accountEntity.get();
            Long empId = account.getEmployeeEntity().getEmpId();
            EmployeeEntity emp = employeeDao.findByEmpId(empId);

            // 정보 등록 여부 확인
            Boolean infoCheck = true;
            Map response = new HashMap<String, Object>();
            if (emp.getEmpEmail() == null || emp.getEmpEmail().isBlank()) {
                infoCheck = false;
                response.put("message", "이메일이 등록되지 않았습니다. 등록해주세요.");
            }
            if (emp.getEmpSkill() == null || emp.getEmpSkill().isBlank()) {
                infoCheck = false;
                response.put("message", "개인 스킬이 등록되지 않았습니다. 등록해주세요.");
            }
            if (emp.getEmpEdu() == null || emp.getEmpEdu().isBlank()) {
                infoCheck = false;
                response.put("message", "학력이 등록되지 않았습니다. 등록해주세요.");
            }
            if (emp.getEmpWorkEx() == null) {
                infoCheck = false;
                response.put("message", "경력이 등록되지 않았습니다. 등록해주세요.");
            }
            if (emp.getEmpPh() == null || emp.getEmpPh().isBlank()) {
                infoCheck = false;
                response.put("message", "경력이 등록되지 않았습니다. 등록해주세요.");
            }

            //로그인 성공
            session.setMaxInactiveInterval(1800);
            session.setAttribute("AccId", loginResult.getAccId());
            session.setAttribute("AuthId", loginResult.getAuthId());
            session.setAttribute("accessGrade", loginResult.getAccessGrade());
            log.info("maxInactiveInterval={}", session.getMaxInactiveInterval());
            response.put("AccId", loginResult.getAccId());
            response.put("AuthId", loginResult.getAuthId());
            response.put("accessGrade", loginResult.getAccessGrade());

            if (!infoCheck) {
                response.put("status", 0);
            } else {
                response.put("status", 1);
            }
            return response;
        } else {
            //로그인 실패
            Map response = new HashMap<String, Object>();
            response.put("message", "아이디 혹은 비밀번호가 올바른지 확인하세요.");
            return response;
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

    // 이메일에 따른 아이디가 일차하는지 여부 확인
    @GetMapping("/help/resetPW/checkAccountInfo")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map checkAccountInfo(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "email") String email) {
        // 이메일에 따른 아이디가 일차하는지 여부
        EmpIdByEmailResponseInterface dto = accountDao.findAuthIdByEmail(email);

        Map response = new HashMap<String, Object>();
        if (dto == null || !dto.getAuth_id().equals(id)) {
            response.put("message", "이메일과 아이디에 일치하는 정보가 없습니다.");
            response.put("status", 0);
        }
        response.put("message", "일치하는 정보가 있습니다.");
        response.put("status", 1);

        return response;
    }

    // 로그인 없이 비밀번호 변경
    @PatchMapping("/help/resetPW/nonLogin")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map resetPwNonLogin(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "new_pw") String newPW,
            @RequestParam(value = "new_pw_re") String newPWRE) {

        AccountPwUpdateRequestDto dto = new AccountPwUpdateRequestDto();
        dto.setNew_pw(newPW);
        dto.setNew_pw_re(newPWRE);
        dto.setOld_pw(newPW);

        System.out.println("id: " + id);

        Optional<AccountEntity> accId = accountDao.findByAuthId(id);

        Map response = accountService.updatePwNonLogin(accId.get().getAccId(), dto);

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
    @ResponseBody
    public List<RoleNonAccessResponseInterface> roleUnrecognized() {
        List<RoleNonAccessResponseInterface> response = accountService.findAllNonAccessEmp();
        return response;
    }

    // 아이디 찾기 (이메일로 조회)
    @GetMapping("/help/findID")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map roleUnrecognized(
            @RequestParam(value = "email") String email) {
        Map response = accountService.findAuthIdByEmail(email);
        return response;
    }
}
