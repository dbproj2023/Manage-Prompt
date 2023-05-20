package com.dbproj.manageprompt.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@RequiredArgsConstructor
@Repository
public class CertificationDao {
    private final String PREFIX = "email:";
    private final long LIMIT_TIME = 60 * 5L;  // TTL : 5분

    private final StringRedisTemplate redisTemplate;

    // redis 에 email에 따른 인증번호 저장
    public void createEmailCertification(String email, String certificationNumber) {
        redisTemplate.opsForValue().set(PREFIX + email, certificationNumber, Duration.ofSeconds(LIMIT_TIME));
    }

    // email 로 인증번호 반환
    public String getEmailCertificationByEmail(String email) {
        return redisTemplate.opsForValue().get(PREFIX + email);
    }

    // email 로 해당 redis 데이터 제거
    public void removeEmailCertification(String email) {
        redisTemplate.delete(PREFIX + email);
    }

    // email로 인증번호 존재 여부 확인
    public boolean hasKey(String email) {
        return redisTemplate.hasKey(PREFIX + email);
    }
}
