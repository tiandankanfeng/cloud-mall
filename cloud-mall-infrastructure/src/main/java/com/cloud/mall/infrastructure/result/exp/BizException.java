package com.cloud.mall.infrastructure.result.exp;

/**
 * @Author: 夜痕
 * @Date: 2022-02-03 4:57 下午
 * @Description:
 */
public class BizException extends RuntimeException{
    private static final long serialVersionUID = -6631157393186098033L;

    private final BizExceptionProperties bizExceptionProperties;

    public BizException() {
        super();
        this.bizExceptionProperties = BizExceptionProperties.UNKNOWN_SERVER_ERROR;
    }

    public BizException(final String errorMsg) {
        super(errorMsg);
        this.bizExceptionProperties = BizExceptionProperties.UNKNOWN_SERVER_ERROR;
    }
}
