package com.dbproj.manageprompt.service;

import com.dbproj.manageprompt.dao.AccountDao;
import com.dbproj.manageprompt.dto.AccountCreateRequestDto;
import com.dbproj.manageprompt.dto.AccountRequestDto;
import com.dbproj.manageprompt.entity.AccountEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AccountService {

    private final AccountDao accountDao;
    //private final AccountRequestDto requestDto;
    //private final AccountResponseDto accountResponseDto;

    //신규 정보 등록 - 초기 계정 생성
    public Long create(AccountCreateRequestDto accountCreateDto) {
        //관리자 - 직원 정보 등록

        accountCreateDto.setAcc_id(accountCreateDto.getAcc_id());
        accountCreateDto.setAuth_id(accountCreateDto.getAuth_id());
        accountCreateDto.setAuth_pw(accountCreateDto.getAuth_pw());

        AccountEntity newAccount = accountCreateDto.toEntity();
        newAccount = accountDao.save(newAccount);

        return newAccount.getAccId();


        //직원 - 계정 정보 업데이트 --> 따로 작성해야 함
    }

    public AccountRequestDto login(AccountRequestDto memberDto) {
        Optional<AccountEntity> byAuthId =  accountDao.findByAuthId(memberDto.getAuthId());
        if (byAuthId.isPresent()) {
            //조회 결과 존재 (아이디 존재)
            AccountEntity accountEntity = byAuthId.get();
            if (accountEntity.getAuthPw().equals(memberDto.getAuthPw())) {
                //비밀번호 일치
                AccountRequestDto dto = AccountRequestDto.toDto(accountEntity);
                return dto;
            } else {
                //비밀번호 불일치
                return null;
            }
        } else {
            //조회 결과 없음 (아이디 없음)
            return null;
        }
    }
}
