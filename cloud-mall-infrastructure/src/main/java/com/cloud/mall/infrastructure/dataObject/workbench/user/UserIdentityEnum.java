package com.cloud.mall.infrastructure.dataObject.workbench.user;

/**
 * @Author: 夜痕
 * @Date: 2022-01-24 2:13 下午
 * @Description: 用户角色枚举
 */
public enum UserIdentityEnum {
    // code 默认值为 0, 因此从 1开始定义
    ORDINARY_USER(1, "ORDINARY_USER"),
    ADMIN(2, "ADMIN");

    private final Integer code;

    private final String desc;

    UserIdentityEnum(final Integer code, final String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
