package com.dbproj.manageprompt.service;

import com.dbproj.manageprompt.common.exception.NotFoundException;
import com.dbproj.manageprompt.dao.ClientInfoDao;
import com.dbproj.manageprompt.dto.ClientResponseDto;
import com.dbproj.manageprompt.dto.ClientUpdateRequestDto;
import com.dbproj.manageprompt.entity.ClientInfoEntity;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClientService {

    private final ClientInfoDao clientInfoDao;

    // 발주처 상세 조회
    @Transactional(readOnly = true)
    public ClientResponseDto findOne(long clientId) {
        ClientInfoEntity client = findClient(clientId);
        return ClientResponseDto.from(client);
    }

    // findClient method
    @Transactional(readOnly = true)
    public ClientInfoEntity findClient(long clientId) {
        ClientInfoEntity client = clientInfoDao.findById(clientId).orElseThrow(() ->
                new IllegalArgumentException("해당 발주처는 존재하지 않습니다. => " + clientId));
        return client;
    }

    // 발주처 담당자 정보 수정
    public long update(long clientId, ClientUpdateRequestDto requestDto) {
        ClientInfoEntity updateClient = clientInfoDao.findById(clientId).orElseThrow(NotFoundException::new);
        updateClient.update(
                requestDto.getClientEmpName(),
                requestDto.getClientEmpPh(),
                requestDto.getClientEmpEmail()
        );

        return clientInfoDao.save(updateClient).getClientId();
    }
}
