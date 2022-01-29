package com.cloud.mall.app.aop;

import java.util.Objects;

import javax.servlet.http.HttpSession;

import cn.hutool.core.util.StrUtil;
import com.cloud.mall.infrastructure.data.dao.user.UserWrapper;
import com.cloud.mall.infrastructure.dataObject.workbench.user.UserDO;
import com.cloud.mall.infrastructure.session.PortalSession;
import com.cloud.mall.infrastructure.utils.SessionUtil;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserWrapper userWrapper;

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
    public Object doAroundMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取登陆态信息
        final ServletRequestAttributes servletRequestAttributes
            = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
        final HttpSession session = servletRequestAttributes.getRequest().getSession();
        /**
         * 设定：登陆成功的用户 session中存在 userId, userNick.
         */
        final Long userId = (Long)session.getAttribute("userId");
        val userNick = (String)session.getAttribute("userNick");

        if (0 == userId || Objects.isNull(userId) || StrUtil.isBlank(userNick)) {
            return new Object();
        }

        final UserDO userDO = this.userWrapper.queryByUserId(userId);
        final Boolean authorized = Objects.nonNull(userDO) ?
            userDO.getAccount().equals(userNick) : Boolean.FALSE;

        // todo, can make new aspect or use afterThrowing to handle exp.
        if (authorized) {

            // t根据 nick查询用户昵称并且绑定到当前会话中去
            var portalSession = SessionUtil.currentSession();
            if (Objects.isNull(portalSession)) {
                portalSession = PortalSession.builder()
                    .userId(userId)
                    .userNick(userNick)
                    .userIdentityEnum(userDO.getUserIdentity())
                    .build();
                // bind
                SessionUtil.setCurrentSession(portalSession);
            }
            // t将查找到的信息与当前请求线程进行绑定, 每个请求对应的便是线程的调度(please care the subsequent clean up ops.)
            // 返回失败相关信息(甚至可以直接抛出信息校验相关异常)
            final Object proceed = joinPoint.proceed();
            // 清理会话信息 - 这块实际上只在线程执行完 app对应入口方法后才进行会话信息的清除，如果是方法的内部调用的话经由上述的校验会话
            // 信息被绑定到当前线程后始终会存在
            SessionUtil.remove();
            // todo, targetMethod invoke over, can do something about Statistics.
            return proceed;
        } else {
            return new Object();
        }
    }
}
