package com.cloud.mall.app.aop;

import javax.servlet.http.HttpSession;

import cn.hutool.core.util.StrUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Author: 夜痕
 * @Date: 2022-01-24 2:25 下午
 * @Description: 门户会话切面
 * 所谓切面：切点信息 + 增强信息
 */
@Aspect
@Component
public class PortalSessionAspect {

    @Pointcut("@annotation(com.cloud.mall.app.aop.annotaion.PortalSessionAnnotation)")
    public void sessionPointCut() {
    }

    /**
     * 由于 around在特定情况下无法终止原方法的执行, 此处使用环绕通知
     * todo：针对方法返回值进行状态码封装
     *
     * @param joinPoint
     */
    @Around("sessionPointCut()")
    public static Object doAroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取登陆态信息
        ServletRequestAttributes servletRequestAttributes
            = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
        HttpSession session = servletRequestAttributes.getRequest().getSession();
        String loginNick = (String)session.getAttribute("userNick");
        // todo, 根据 nick查询用户昵称并且绑定到当前会话中去
        // todo, 将查找到的信息与当前请求线程进行绑定, 每个请求对应的便是线程的调度(please care the subsequent clean up ops.)
        // 返回失败相关信息(甚至可以直接抛出信息校验相关异常)
        // todo, can make new aspect or use afterThrowing to handle exp.
        if (StrUtil.isNotBlank(loginNick)) {
            Object proceed = joinPoint.proceed();
            // todo, targetMethod invoke over, can do something about Statistics.
            return proceed;
        } else
            return new Object();
    }
}
