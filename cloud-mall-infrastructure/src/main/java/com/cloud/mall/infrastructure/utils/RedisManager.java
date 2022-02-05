package com.cloud.mall.infrastructure.utils;

import java.util.concurrent.TimeUnit;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: 夜痕
 * @Date: 2022-02-05 2:00 下午
 * @Description:
 */
@Slf4j
@Component
public class RedisManager {
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 对于所有存进 redis的强制设置过期时间
     * @param key
     * @param value
     * @param expireTime
     */
    public boolean set(final String key, final Object value, final Long expireTime) {
        boolean opsRes = false;
        try {
            opsRes = this.set(key, value, expireTime, TimeUnit.SECONDS);
        } catch (final Exception e) {
            RedisManager.log.warn("key:{}, value:{} ops failed!", key, value);
        }
        return opsRes;
    }

    public boolean set(final String key, final Object value, final Long expireTime, final TimeUnit timeUnit) {
        boolean opsRes = false;
        try {
            this.redisTemplate.opsForValue().set(key, value, expireTime, timeUnit);
            opsRes = Boolean.TRUE;
        } catch (final Exception e) {
            RedisManager.log.warn("key:{}, value:{} ops failed!", key, value);
        }
        return opsRes;
    }

    public Object get(final String key) {
        if (StrUtil.isBlank(key)) {
            return null;
        }
        return this.redisTemplate.opsForValue().get(key);
    }

    /**
     * 指定缓存过期时间
     * @param key
     * @param expireTime
     * @param timeUnit
     * @return
     */
    public boolean expire(final String key, final Long expireTime, final TimeUnit timeUnit) {
        try {
            this.redisTemplate.expire(key, expireTime, timeUnit);
            return true;
        } catch (final Exception exp) {
            RedisManager.log.warn("key:{} ops given expireTime failed!", key);
        }
        return false;
    }
}
