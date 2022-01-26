package com.cloud.mall.domain.workbench.msg.model;

import java.util.Arrays;

import cn.hutool.core.util.StrUtil;

/**
 * @Author: 夜痕
 * @Date: 2022-01-25 7:35 下午
 * @Description:
 */
public enum MsgCodeEnum {
    // SMS-没有该用户账户
    NO_USER_ACCOUNT(-1, "SMS-没有该用户账户"),
    // SMS-接口密钥不正确
    INTERFACE_KEY_INCORRECT(-2, "SMS-接口密钥不正确"),
    // SMS-MD5接口密钥加密不正确
    INTERFACE_KEY_ENCRYPT_INCORRECT(-21, "SMS-MD5接口密钥加密不正确"),
    // 短信数量不足
    INSUFFICIENT_TEXT_MESSAGE(-3, "短信数量不足"),
    // SMS-该用户被禁用
    BANNED_USER(-11, "SMS-该用户被禁用"),
    // SMS-短信内容出现非法字符
    ILLEGAL_CONTENT(-14, "SMS-短信内容出现非法字符"),
    // SMS-手机号格式不正确
    INCORRECT_PHONE_FORMATE(-4, "SMS-手机号格式不正确"),
    // SMS-手机号码为空
    NULL_PHONE_NUMBER(-41, "SMS-手机号码为空"),
    // SMS-短信内容为空
    NULL_MESSAGE_CONTENT(-42, "SMS-短信内容为空"),
    // SMS-短信签名格式不正确接口签名格式为：【签名内容】
    INCORRECT_MESSAGE_SIGNATURE(-51, "SMS-短信签名格式不正确接口签名格式为：【签名内容】"),
    // SMS-IP限制
    IP_LIMIT(-6, "SMS-IP限制"),
    // 状态码不存在
    STATUS_CODE_NOT_PRESENT(-100, "状态码不存在, 请联系 SMS平台客服!"),
    MSG_SND_SUCCESS(200, "SMS-f发送成功!");

    private final int code;
    private final String desc;

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    MsgCodeEnum(final int code, final String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static MsgCodeEnum getMsgEnumByCode(final Integer code) {
        if (code > 0) {
            return MsgCodeEnum.MSG_SND_SUCCESS;
        }
       return Arrays.stream(MsgCodeEnum.values())
            .filter(msgCodeEnum -> msgCodeEnum.getCode() == code)
            .findFirst()
           // 一般来说都会有对应的状态码
            .orElse(MsgCodeEnum.STATUS_CODE_NOT_PRESENT);
    }
}
