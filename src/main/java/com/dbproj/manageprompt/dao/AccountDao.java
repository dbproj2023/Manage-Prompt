package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.dto.AccountRequestDto;
import com.dbproj.manageprompt.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountDao extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByAuthId(String AuthId);
    //Optional<AccountEntity> findByAccId(String AccId);
    @Query(value = "SELECT a, b FROM AccountEntity a " + "LEFT JOIN a.employeeEntity b " + "WHERE a.accId = :accId")
    Optional<AccountEntity> findByaccId(@Param("accId") Long accId);

    boolean existsByAuthId(String auth_id);

    AccountEntity findByEmployeeEntity_EmpId(Long emp_id);
}
