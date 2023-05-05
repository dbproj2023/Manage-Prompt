package com.dbproj.manageprompt.service;

import com.dbproj.manageprompt.dao.ClientInfoDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClientService {

    private final ClientInfoDao clientInfoDao;

    // 발주처 등록

    // 발주처 수정

    // 발주처 삭제
    public void delete(long client_id) {
        clientInfoDao.deleteById(client_id);
    }
}
