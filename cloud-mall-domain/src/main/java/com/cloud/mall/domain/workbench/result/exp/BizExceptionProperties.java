package com.cloud.mall.domain.workbench.result.exp;

import java.util.Arrays;

import cn.hutool.core.util.StrUtil;

/**
 * @Author: 夜痕
 * @Date: 2022-02-01 1:24 下午
 * @Description: 枚举异常信息
 */
public enum BizExceptionProperties {

    USER_NOT_AUTHORIZED("用户未登录或信息认证失败"),
    PARAM_VALIDATE_NOT_PASS("参数校验未通过!"),
    /**
     * default
     */
    UNKNOWN_SERVER_ERROR("服务端异常"),
    REPEATABLE_ACCOUNT("用户名重复, 请更换一个新的名称再进行此操作!");

    private final String msg;

    BizExceptionProperties(final String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    static BizExceptionProperties getBizExceptionByMsg(final String msg) {
        if (StrUtil.isBlank(msg)) {
            return null;
        }
        return Arrays.stream(BizExceptionProperties.values())
            .filter(bizExp -> bizExp.msg.equals(msg))
            .findFirst()
            .orElse(null);
    }
}