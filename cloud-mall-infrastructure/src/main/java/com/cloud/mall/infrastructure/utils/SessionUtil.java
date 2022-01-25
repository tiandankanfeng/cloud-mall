package com.cloud.mall.infrastructure.utils;

import com.cloud.mall.infrastructure.session.PortalSession;
import lombok.experimental.UtilityClass;

/**
 * @Author: 夜痕
 * @Date: 2022-01-24 2:21 下午
 * @Description: 门户会话工具类
 */
@UtilityClass
public class SessionUtil {
    /**
     * 使用父子进程间可进行传递的 tl
     * 赋值时机：thread create 浅拷贝
     */
    private final InheritableThreadLocal<PortalSession> inheritableThreadLocal = new InheritableThreadLocal<>();

    /**
     * 获取当前会话信息
     * @return
     */
    public PortalSession currentSession() {
        return SessionUtil.inheritableThreadLocal.get();
    }

    /**
     * 清理
     */
    public void remove() {
        SessionUtil.inheritableThreadLocal.remove();
    }
}
