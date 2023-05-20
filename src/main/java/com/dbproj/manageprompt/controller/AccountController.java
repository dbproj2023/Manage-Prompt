package com.dbproj.manageprompt.controller;

import com.dbproj.manageprompt.dto.*;
import com.dbproj.manageprompt.service.AccountService;
import com.dbproj.manageprompt.service.EmailAuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONException;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@Slf4j
public class AccountController {

    private final AccountService accountService;

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
    public String updateUser(@ModelAttribute AccountCreateRequestDto memberDTO) {
        accountService.updateUser(memberDTO);
        return "success";
    }


    //로그인
    @PostMapping("/login")
    public String login(@ModelAttribute AccountRequestDto memberDto, HttpSession session) {
        AccountRequestDto loginResult = accountService.login(memberDto);
        if (loginResult != null) {
            //로그인 성공
            session.setAttribute("AccId", loginResult.getAccId());
            session.setAttribute("AuthId", loginResult.getAuthId());
            //session.setAttribute("",loginResult.get);
            return "login";
        } else {
            //로그인 실패
            return "login fail";
        }
    }

    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "logout";
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
}
