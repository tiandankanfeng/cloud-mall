package com.cloud.mall.app.config;

import lombok.experimental.UtilityClass;

/**
 * @Author: 远道
 * @Date: 2022-03-21 1:09 上午
 * @Description:
 */
@UtilityClass
public class AopConstants {

    /**
     * 系统中切面信息, 越往上权重越高
     */
    @UtilityClass
    public class AopOrderConstants {
        /**
         * 鉴权相关
         */
        public final int PORTAL_SESSION_ASPECT = -1;
        /**
         * 限流相关
         */
        public final int PORTAL_INJECTION_ASPECT = 0;
    }

}
