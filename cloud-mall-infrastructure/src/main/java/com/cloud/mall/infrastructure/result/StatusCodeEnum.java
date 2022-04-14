package com.cloud.mall.infrastructure.result;

import java.util.Arrays;

/**
 * @Author: 夜痕
 * @Date: 2022-02-01 1:34 下午
 * @Description:
 */
public enum StatusCodeEnum {

    SUCCESS(20000, "服务端响应正常"),
    USER_BANNED(403, "用户没有权限访问对应资源"),
    SERVLET_INNER_ERROR(500, "服务端内部错误");

    private final Integer code;

    private final String desc;

    StatusCodeEnum(final Integer code, final String desc) {
        this.code = code;
        this.desc = desc;
    }

   static StatusCodeEnum getStatusCodeEnumByCode(final Integer code) {
        if (0 == code || code == null) {
            return null;
        }

        return Arrays.stream(StatusCodeEnum.values())
            .filter(statusCodeEnum -> statusCodeEnum.code.equals(code))
            .findFirst()
            .orElse(null);
   }

    public Integer getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
