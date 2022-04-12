package com.cloud.mall.domain.workbench.tags.model;

import cn.hutool.json.JSONObject;

/**
 * @Author: 远道
 * @Date: 2022-04-11 9:04 下午
 * @Description:
 */
public enum TagEnum {

    FACT(1, "事实标签"),
    RULE(2, "规则标签"),
    PREDICT(3, "预测标签");

    private final Integer code;

    private final String desc;

    public Integer getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    TagEnum(final Integer code, final String desc) {
        this.code = code;
        this.desc = desc;
    }
}
