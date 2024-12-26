package com.plantify.pay.config;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RedisLock {

    private final StringRedisTemplate stringRedisTemplate;

    public boolean tryLock(String key, long timeoutMillis) {
        String value = UUID.randomUUID().toString();
        Boolean success = stringRedisTemplate.opsForValue()
                .setIfAbsent(key, value, Duration.ofMillis(timeoutMillis));
        return Boolean.TRUE.equals(success);
    }

    public void unlock(String key) {
        stringRedisTemplate.delete(key);
    }
}
