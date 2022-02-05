package com.cloud.mall.infrastructure.dataObject.workbench.user;

/**
 * @Author: 夜痕
 * @Date: 2022-01-29 8:31 下午
 * @Description:
 */
public enum SexEnum {

    WOMAN(1, "W"),
    MAN(2, "M");

    private final Integer code;

    private final String desc;

    SexEnum(final Integer code, final String desc) {
        this.code = code;
        this.desc = desc;
    }
}
