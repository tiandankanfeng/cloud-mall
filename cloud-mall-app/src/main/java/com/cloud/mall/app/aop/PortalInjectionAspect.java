package com.cloud.mall.app.aop;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.cloud.mall.app.aop.annotaion.PortalInjectionAnnotation;
import com.cloud.mall.app.config.AopConstants.AopOrderConstants;
import com.cloud.mall.infrastructure.result.exp.BizException;
import com.cloud.mall.infrastructure.result.exp.BizExceptionProperties;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Author: 远道
 * @Date: 2022-03-21 1:13 上午
 * @Description:
 */
@Aspect
@Component
@Order(AopOrderConstants.PORTAL_INJECTION_ASPECT)
@Slf4j
public class PortalInjectionAspect {

    private static Map<Method, RateLimiter> limitMap;

    static {
        PortalInjectionAspect.limitMap = new ConcurrentHashMap<>();
    }

    @Pointcut("@annotation(com.cloud.mall.app.aop.annotaion.PortalInjectionAnnotation)")
    public void injectionAspect() {
    }

    @Around("injectionAspect()")
    public Object doAroundMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        // parse annotation
        final MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        final PortalInjectionAnnotation portalInjectionAnnotation = methodSignature.getMethod().getAnnotation(
            PortalInjectionAnnotation.class);
        PortalInjectionAspect.limitMap.get(methodSignature.getMethod());

        // lazy init
        if (Objects.isNull(methodSignature.getMethod())) {
            // Guava rateLimiter
            final RateLimiter rateLimiter = RateLimiter.create(portalInjectionAnnotation.touchLimiting(), 3L,
                portalInjectionAnnotation.timeUnit());
            PortalInjectionAspect.limitMap.put(methodSignature.getMethod(), rateLimiter);
        }

        final RateLimiter rateLimiter = PortalInjectionAspect.limitMap.get(methodSignature.getMethod());
        // try lock
        try {
            if (rateLimiter.tryAcquire()) {
                return joinPoint.proceed();
            }
            return new BizException(BizExceptionProperties.METHOD_ARE_NOT_ALLOWED_ACCESS.getMsg());
        } catch (final Throwable e) {
            PortalInjectionAspect.log.warn("method:{} happens exception:{}", methodSignature.getMethod(), e.getMessage());
            // let outer aspect solve exp
            throw e;
        }

    }
}
