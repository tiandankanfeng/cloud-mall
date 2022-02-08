package com.cloud.mall.infrastructure.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 夜痕
 * @Date: 2022-02-01 1:17 下午
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultDto<T> {
    /**
     * 真实数据
     */
    private T data;
    /**
     * 请求状态
     */
    private Boolean success;
    /**
     * 请求响应状态码
     */
    private int code;
    /**
     * 异常信息
     */
    private String msg;

    public ResultDto(final T data) {
        this.data = data;
    }
}
