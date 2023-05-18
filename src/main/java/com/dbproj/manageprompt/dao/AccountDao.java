package com.dbproj.manageprompt.dao;

import com.dbproj.manageprompt.dto.AccountRequestDto;
import com.dbproj.manageprompt.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountDao extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByAuthId(String AuthId);
}
