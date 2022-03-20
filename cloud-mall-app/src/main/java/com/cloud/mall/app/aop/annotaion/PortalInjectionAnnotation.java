package com.cloud.mall.app.aop.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import com.cloud.mall.app.config.AopConstants.AopOrderConstants;
import org.springframework.core.annotation.Order;

/**
 * @Author: 远道
 * @Date: 2022-03-21 12:52 上午
 * @Description: 并且所有方法都需要触发限流策略
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PortalInjectionAnnotation {
    /**
     * 限流周期单位
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 限流时长
     * @return
     */
    long duration() default 1L;

    /**
     * 限流访问次数 1000
     * @return
     */
    long touchLimiting() default 1000L;
}
