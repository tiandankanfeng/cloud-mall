package com.cloud.mall.app.aop;

import java.util.Objects;

import javax.servlet.http.Cookie;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.cloud.mall.app.aop.annotaion.PortalSessionAnnotation;
import com.cloud.mall.infrastructure.data.dao.user.UserWrapper;
import com.cloud.mall.infrastructure.dataObject.workbench.user.UserDO;
import com.cloud.mall.infrastructure.dataObject.workbench.user.UserIdentityEnum;
import com.cloud.mall.infrastructure.result.ResultDto;
import com.cloud.mall.infrastructure.result.StatusCodeEnum;
import com.cloud.mall.infrastructure.result.exp.BizException;
import com.cloud.mall.infrastructure.result.exp.BizExceptionProperties;
import com.cloud.mall.infrastructure.session.PortalSession;
import com.cloud.mall.infrastructure.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
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
@Slf4j
public class PortalSessionAspect {

    @Autowired
    private UserWrapper userWrapper;

    @Pointcut("@annotation(com.cloud.mall.app.aop.annotaion.PortalSessionAnnotation)")
    public void sessionPointCut() {
    }

    /**
     * 由于 around在特定情况下无法终止原方法的执行, 此处使用环绕通知
     *
     * @param joinPoint
     */
    @Around("sessionPointCut()")
    public Object doAroundMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取登陆态信息
        final ServletRequestAttributes servletRequestAttributes
            = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
        final Cookie[] cookies = servletRequestAttributes.getRequest().getCookies();

        // 获取注解信息
        final MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        final PortalSessionAnnotation portalSessionAnnotation = signature.getMethod().getAnnotation(
            PortalSessionAnnotation.class);
        final boolean acquireUserAdmin = portalSessionAnnotation.expectedUserAdmin();

        /**
         * 设定：登陆成功的用户 cookie中存在 userId, userNick.
         */
        Long userId = 0L;
        String userNick = null;
        for (final Cookie cookie : cookies) {
            userId = cookie.getName().equals("userId") ? Long.valueOf(cookie.getValue()) : userId;
            userNick = cookie.getName().equals("userNick") ? cookie.getValue() : userNick;
        }

        final var resultDto = new ResultDto();
        try {
            if (0 == userId || Objects.isNull(userId) || StrUtil.isBlank(userNick)) {
                return new BizException(BizExceptionProperties.USER_NOT_AUTHORIZED.getMsg());
            }

            final UserDO userDO = this.userWrapper.queryByUserId(userId);
            final Boolean authorized = Objects.nonNull(userDO) ?
                userDO.getAccount().equals(userNick) : Boolean.FALSE;
            if (acquireUserAdmin && !UserIdentityEnum.ADMIN.equals(userDO.getUserIdentity())) {
                throw new BizException(BizExceptionProperties.USER_IDENTITY_VALIDATE_NOT_PASS.getMsg());
            }

            if (authorized) {

                // 根据 nick查询用户昵称并且绑定到当前会话中去
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
                // 将查找到的信息与当前请求线程进行绑定, 每个请求对应的便是线程的调度(please care the subsequent clean up ops.)
                // 返回失败相关信息(甚至可以直接抛出信息校验相关异常)
                val proceed = joinPoint.proceed();
                resultDto.setData(proceed);

                // todo, targetMethod invoke over, can do something about Statistics.
            } else {
                resultDto.setSuccess(Boolean.FALSE);
                resultDto.setMsg(BizExceptionProperties.USER_NOT_AUTHORIZED.getMsg());
                resultDto.setCode(StatusCodeEnum.USER_BANNED.getCode());
            }
        } catch (final Exception exp) {
            resultDto.setMsg(exp.getMessage());
            PortalSessionAspect.log.info("ExpStackTrace:{}", exp.getStackTrace());
        } finally {
            if (StrUtil.isBlank(resultDto.getMsg())) {
                resultDto.setSuccess(Boolean.TRUE);
            }
            resultDto.setMsg(BizExceptionProperties.METHOD_INVOKE_SUCCESS.getMsg());
            resultDto.setCode(StatusCodeEnum.SUCCESS.getCode());
            // 清理会话信息 - 这块实际上只在线程执行完 app对应入口方法后才进行会话信息的清除，如果是方法的内部调用的话经由上述的校验会话
            // 信息被绑定到当前线程后始终会存在
            SessionUtil.remove();
        }

        return JSONUtil.toJsonStr(resultDto);
    }

    @AfterThrowing(pointcut = "sessionPointCut()", throwing = "throwable")
    public void afterThrowingAdvice(final JoinPoint joinPoint, final Throwable throwable) {
        // todo：这里可以来统计下异常出现的次数，但无法终止异常
    }
}
