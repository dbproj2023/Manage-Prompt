package com.dbproj.manageprompt.controller;

import com.dbproj.manageprompt.dto.*;
import com.dbproj.manageprompt.entity.AccessInfoEntity;
import com.dbproj.manageprompt.entity.AccountEntity;
import com.dbproj.manageprompt.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@Slf4j
public class AccountController {

    private final AccountService accountService;

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
}
