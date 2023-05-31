package com.dbproj.manageprompt.service;

import com.dbproj.manageprompt.common.exception.NotFoundException;
import com.dbproj.manageprompt.dao.AccessInfoDao;
import com.dbproj.manageprompt.dao.AccountDao;
import com.dbproj.manageprompt.dao.EmployeeDao;
import com.dbproj.manageprompt.dto.*;
import com.dbproj.manageprompt.entity.AccessInfoEntity;
import com.dbproj.manageprompt.entity.AccountEntity;
import com.dbproj.manageprompt.entity.EmployeeEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
        employeeRequestDto.setEmp_ssn(accountCreateDto.getEmp_ssn());
        employeeRequestDto.setEmp_edu(accountCreateDto.getEmp_edu());
        employeeRequestDto.setEmp_email("");
        employeeRequestDto.setEmp_workex(accountCreateDto.getEmp_work_ex());
        employeeRequestDto.setEmp_skill(accountCreateDto.getEmp_skill());
        employeeRequestDto.setEmp_ph("");
        employeeRequestDto.setCreated_at(LocalDateTime.now());

        EmployeeEntity newEmployee = employeeRequestDto.toEntity();
        employeeDao.save(newEmployee);

        //관리자 - 초기 계정 등록
        accountCreateDto.setAcc_id(accountCreateDto.getAcc_id());
        accountCreateDto.setAuth_id(accountCreateDto.getAuth_id());
        accountCreateDto.setAuth_pw(accountCreateDto.getAuth_pw());
        accountCreateDto.setEmployee(newEmployee);
        Optional<AccessInfoEntity> accessInfoEntity = accessInfoDao.findById(9);
        accountCreateDto.setAccess_Info(accessInfoEntity.get());

        AccountEntity newAccount = accountCreateDto.toEntity();
        newAccount = accountDao.save(newAccount);

        return newAccount.getAccId();
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
        Optional<AccessInfoEntity> accessInfoEntity = accessInfoDao.findById(9);
        memberDTO.setAccess_Info(accessInfoEntity.get());
        Optional<EmployeeEntity> entity = employeeDao.findById(memberDTO.getEmp_id());
        employeeDao.save(EmployeeEntity.toUpdateEmployeeEntity(memberDTO, entity));
        Optional<EmployeeEntity> employeeEntity = employeeDao.findById(memberDTO.getEmp_id());
        memberDTO.setEmployee(employeeEntity.get());
        accountDao.save(AccountEntity.toUpdateAccountEntity(memberDTO));

    }

    public boolean checkAuthIdDuplicate(String auth_id) {
        return accountDao.existsByAuthId(auth_id);
    }

    public Map updatePw(Long accid, AccountPwUpdateRequestDto accountPwUpdateRequestDto) {
        AccountEntity accountEntity = accountDao.findById(accid).orElseThrow(NotFoundException::new);
        Map response = new HashMap<String, Object>();
        if (!accountEntity.getAuthPw().equals(accountPwUpdateRequestDto.getOld_pw())) {
            log.info(accountEntity.getAuthPw());
            log.info(accountPwUpdateRequestDto.getOld_pw());
            response.put("message", "이전 비밀번호가 일지하지 않습니다.");
            response.put("status", 0);
        }
        else if (!accountPwUpdateRequestDto.getNew_pw().equals(accountPwUpdateRequestDto.getNew_pw_re())) {
            response.put("message", "비밀번호가 일치하지 않습니다.");
            response.put("status", 1);
        }else {
            accountEntity.update(accountPwUpdateRequestDto.getNew_pw());
            accountDao.save(accountEntity);
            response.put("message", "비밀번호가 변경되었습니다.");
            response.put("status",2);
        }
        return response;
    }

    public Map updatePwNonLogin(Long accid, AccountPwUpdateRequestDto accountPwUpdateRequestDto) {
        AccountEntity accountEntity = accountDao.findById(accid).orElseThrow(NotFoundException::new);
        Map response = new HashMap<String, Object>();
        if (!accountPwUpdateRequestDto.getNew_pw().equals(accountPwUpdateRequestDto.getNew_pw_re())) {
            response.put("message", "비밀번호가 일치하지 않습니다.");
            response.put("status", 1);
        }else {
            accountEntity.update(accountPwUpdateRequestDto.getNew_pw());
            accountDao.save(accountEntity);
            response.put("message", "비밀번호가 변경되었습니다.");
            response.put("status",2);
        }
        return response;
    }

    public Map roleUpdate(AccessUpdateRequestDto updateDto) {
        log.info(String.valueOf(updateDto.getEmp_id()));
        AccountEntity accountEntity = accountDao.findByEmployeeEntity_EmpId(updateDto.getEmp_id());
        Map response = new HashMap<String, Object>();
        if (accountEntity != null) {
            response.put("message", "권한이 변경되었습니다.");
            response.put("status", 1);
            Optional<AccessInfoEntity> accessInfoEntity = accessInfoDao.findById(updateDto.getAccess_grade());
            accountEntity.updateAccess(accessInfoEntity.get());
            accountDao.save(accountEntity);
        } else {
            response.put("message", "오류가 발생했습니다.");
            response.put("status", 0);
        }
        return response;
    }

    // 권한 미부여(9번) 직원 조회
    public List<RoleNonAccessResponseInterface> findAllNonAccessEmp() {
        List<RoleNonAccessResponseInterface> dto = accountDao.findAllNonAccessEmp();

        return dto;
    }

    // 아이디 찾기 (이메일로 조회)
    public Map findAuthIdByEmail(String email) {
        EmpIdByEmailResponseInterface dto = accountDao.findAuthIdByEmail(email);

        Map response = new HashMap<String, Object>();
        if (dto == null) {
            response.put("massage", "관련 정보가 없습니다.");
            response.put("status", 0);

            return response;
        }
        String authId = dto.getAuth_id();
        String securityFormmated = authId.substring(0, authId.length()-4) + "****";
        response.put("auth_id", securityFormmated);
        response.put("status", 1);

        return response;
    }
}
