package com.dbproj.manageprompt.controller;

import com.dbproj.manageprompt.dto.AccountCreateRequestDto;
import com.dbproj.manageprompt.dto.AccountRequestDto;
import com.dbproj.manageprompt.dto.AccountResponseDto;
import com.dbproj.manageprompt.dto.IdResponseDto;
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
    //관리자가 초기 설정한 계정으로 로그인한 계정 아이디 session으로 불러오기
    @GetMapping("/user")
    public String updateFrom(HttpSession session, Model model) {
        String myauthid = (String) session.getAttribute("AuthId");
        AccountRequestDto memberDTO = accountService.updateForm(myauthid);
        model.addAttribute("updateMember", memberDTO);
        log.info(myauthid);
        return "user";
    }
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
            return "login";
        } else {
            //로그인 실패
            return "login fail";
        }
    }




}
