package com.cloud.mall.infrastructure.result.exp;

import java.util.Arrays;

import cn.hutool.core.util.StrUtil;

/**
 * @Author: 夜痕
 * @Date: 2022-02-01 1:24 下午
 * @Description: 枚举异常信息
 */
public enum BizExceptionProperties {

    USER_NOT_AUTHORIZED("用户未登录或信息认证失败"),
    USER_IDENTITY_VALIDATE_NOT_PASS("用户角色校验未通过!"),
    PARAM_VALIDATE_NOT_PASS("参数校验未通过!"),
    ACCOUNT_ALREADY_USERD_BY_OTHERS("账户已被其他人使用, 请更换"),
    /**
     * default
     */
    UNKNOWN_SERVER_ERROR("服务端异常"),
    REPEATABLE_ACCOUNT("用户名重复, 请更换一个新的名称再进行此操作!"),
    CAPTCHA_VALIDATE_NOT_PASS("验证码验证不通过或者已过期!"),
    FILE_UPLOAD_FAILED("文件上传失败!"),
    FILE_NOT_MEET_REQUIREMENT("上传的文件不符合要求"),
    METHOD_INVOKE_SUCCESS("方法请求成功!"),
    MSG_CODE_VALIDATE_NOT_PASS("手机号码校验不通过"),
    GRAPH_CAPTCHA_VALIDATE_NOT_PASS("图形验证码验证不通过"),
    ACCOUNT_NOT_ALLOW_MODIFY("用户账户不允许更改!");

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