package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.AccessInfoEntity;
import com.dbproj.manageprompt.entity.AccountEntity;
import com.dbproj.manageprompt.entity.EmployeeEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AccountCreateRequestDto {
    private Long acc_id;
    private String auth_id;
    private String auth_pw;
    private EmployeeEntity employee;
    private AccessInfoEntity access_Info;
    private Long emp_id;
    private String emp_ssn;
    private String emp_name;
    private String emp_email;
    private Integer emp_work_ex;
    private String emp_skill;
    private String emp_edu;
    private Integer access_grade;
    private LocalDateTime created_at;
    private String emp_ph;

    public AccountEntity toEntity() {
        AccountEntity account = AccountEntity.builder()
                .accId(acc_id)
                .authId(auth_id)
                .authPw(auth_pw)
                .employeeEntity(employee)
                .accessInfoEntity(access_Info)
                .build();
        return account;
    }
//    public static AccountCreateRequestDto toDto(AccountEntity accountEntity) {
//        AccountCreateRequestDto accountCreateRequestDto = new AccountCreateRequestDto();
//        accountCreateRequestDto.setAcc_id(accountEntity.getAccId());
//        accountCreateRequestDto.setEmp_name(accountEntity);
//        accountCreateRequestDto.setEmp_id();
//        accountCreateRequestDto.setCreated_at();
//        accountCreateRequestDto.setEmp_email();
//        accountCreateRequestDto.setEmp_edu();
//        accountCreateRequestDto.setEmp_skill();
//    }
}
