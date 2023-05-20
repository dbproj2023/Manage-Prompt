package com.dbproj.manageprompt.service;

import com.dbproj.manageprompt.dao.AccessInfoDao;
import com.dbproj.manageprompt.dao.AccountDao;
import com.dbproj.manageprompt.dao.EmployeeDao;
import com.dbproj.manageprompt.dto.AccountCreateRequestDto;
import com.dbproj.manageprompt.dto.AccountRequestDto;
import com.dbproj.manageprompt.dto.EmployeeRequestDto;
import com.dbproj.manageprompt.entity.AccountEntity;
import com.dbproj.manageprompt.entity.EmployeeEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AccountService {

    private final AccountDao accountDao;
    private final EmployeeDao employeeDao;

    private final AccessInfoDao accessInfoDao;

    //신규 정보 등록 - 초기 계정 생성
    public Long create(AccountCreateRequestDto accountCreateDto) {
        //관리자 - 초기 직원 정보 등록
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto();
        employeeRequestDto.setEmp_id(accountCreateDto.getEmp_id());
        employeeRequestDto.setEmp_name(accountCreateDto.getEmp_name());
        employeeRequestDto.setEmp_ssn("");
        employeeRequestDto.setEmp_edu("");
        employeeRequestDto.setEmp_email("");
        employeeRequestDto.setEmp_workex(0);
        employeeRequestDto.setEmp_skill("");

        EmployeeEntity newEmployee = employeeRequestDto.toEntity();
        employeeDao.save(newEmployee);

        //AccessInfoEntity accessInfoEntity = accessInfoDao.findById(9);

        //관리자 - 초기 계정 등록
        accountCreateDto.setAcc_id(accountCreateDto.getAcc_id());
        accountCreateDto.setAuth_id(accountCreateDto.getAuth_id());
        accountCreateDto.setAuth_pw(accountCreateDto.getAuth_pw());
        accountCreateDto.setEmployee(newEmployee);
        //accountCreateDto.setAccess_Info();

        AccountEntity newAccount = accountCreateDto.toEntity();
        newAccount = accountDao.save(newAccount);

        return newAccount.getAccId();


        //직원 - 계정 정보 업데이트 --> 따로 작성해야 함
    }
    //로그인
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

    //신규 정보 등록
    public AccountRequestDto updateForm(String myaccid) {
        Optional<AccountEntity> optionalAccountEntity = accountDao.findByAuthId(myaccid);
        if (optionalAccountEntity.isPresent()) {
            return AccountRequestDto.toDto(optionalAccountEntity.get());
        } else {
            return null;
        }
    }


    public void updateUser(AccountCreateRequestDto memberDTO) {
        accountDao.save(AccountEntity.toUpdateAccountEntity(memberDTO));
        employeeDao.save(EmployeeEntity.toUpdateEmployeeEntity(memberDTO));
    }

    public boolean checkAuthIdDuplicate(String auth_id) {
        return accountDao.existsByAuthId(auth_id);
    }
}
