package com.cloud.mall.infrastructure.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: 夜痕
 * @Date: 2022-02-12 8:57 下午
 * @Description:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Transactional(rollbackFor = Exception.class)
public @interface PortalTransactional {
}
