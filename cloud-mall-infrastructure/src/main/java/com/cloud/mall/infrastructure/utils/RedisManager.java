package com.cloud.mall.infrastructure.utils;

import java.time.Instant;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author: 夜痕
 * @Date: 2022-02-05 2:00 下午
 * @Description:
 */
@Slf4j
@Component
@EnableScheduling
public class RedisManager {
    @Autowired
    private RedisTemplate redisTemplate;

    private static final String REDIS_JOB_KEY = "redis_job_task";

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

    /**
     * 添加任务
     * 获取任务对应 value名称
     * 可以结合 map的方式进行任务迭代执行
     * @param taskName
     * @param instant
     */
    public void addTask(final String taskName, final Instant instant) {
        this.redisTemplate.opsForZSet()
            .add(RedisManager.REDIS_JOB_KEY, taskName, instant.getEpochSecond());
    }

    /**
     * 默认调度：每个 5分钟执行一次
     */
    @Scheduled(cron = "* * 1 * * ?")
    public void doDelayQueue() {
        RedisManager.log.info("spring 默认调度执行");
        final long nows = Instant.now().getEpochSecond();
        // 获取当前时间段内命中的任务
        final Set vals = this.redisTemplate.opsForZSet()
            .range(RedisManager.REDIS_JOB_KEY, 0L, nows);
        // todo, map<TaskName, Runnable>
        vals.stream()
            .forEach(val -> {
                RedisManager.log.info("redis 任务调度执行:{}", val);
            });
        // remove already executed tasks
        this.redisTemplate.opsForZSet()
            .remove(RedisManager.REDIS_JOB_KEY, 0, nows);

    }
}
