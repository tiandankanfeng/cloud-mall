package com.cloud.mall.domain.workbench.user.model;

/**
 * @Author: 夜痕
 * @Date: 2022-01-24 2:13 下午
 * @Description: 用户角色枚举
 */
public enum UserIdentityEnum {
    // code 默认值为 0, 因此从 1开始定义
    ORDINARY_USER(1, "普通用户"),
    ADMIN(2, "管理员");

    private final Integer code;

    private final String desc;

    UserIdentityEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
