package com.cloud.mall.app.aop.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: 夜痕
 * @Date: 2022-01-24 2:56 下午
 * @Description: 基于并非所有请求都需要登陆态, 因此基于注解的方式调配地进行登陆态的注入
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PortalSessionAnnotation {

    /**
     * 期待用户角色 - 默认是普通用户
     * @return
     */
    boolean expectedUserAdmin() default false;
}
