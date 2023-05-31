package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.dto.EmpIdByEmailResponseInterface;
import com.dbproj.manageprompt.dto.RoleNonAccessResponseInterface;

import com.dbproj.manageprompt.entity.AccountEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountDao extends JpaRepository<AccountEntity, Long> {
    @Query(
            value = "select * from account where auth_id=:AuthId",
            nativeQuery = true
    )
    Optional<AccountEntity> findByAuthId(@Param("AuthId") String AuthId);
    //Optional<AccountEntity> findByAccId(String AccId);
    @Query(value = "SELECT a, b FROM AccountEntity a " + "LEFT JOIN a.employeeEntity b " + "WHERE a.accId = :accId")
    Optional<AccountEntity> findByaccId(@Param("accId") Long accId);

    boolean existsByAuthId(String auth_id);
    @Query(
            value = "select * from account where emp_id=:emp_id",
            nativeQuery = true
    )
    AccountEntity findByEmployeeEntity_EmpId(@Param("emp_id") Long emp_id);

    @Query(
            value = "select * from (select emp_id, emp_name from employee) e natural join (select emp_id, auth_id from account where access_grade=9) aa",
            nativeQuery = true
    )
    List<RoleNonAccessResponseInterface> findAllNonAccessEmp();

    // 아이디 찾기 (이메일로 조회)
    @Query(
            value = "select emp_id, auth_id from account a natural join (select emp_id from employee where emp_email=:emp_email) e",
            nativeQuery = true
    )
    EmpIdByEmailResponseInterface findAuthIdByEmail(@Param("emp_email") String emp_email);
}
