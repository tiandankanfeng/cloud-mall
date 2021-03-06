package com.cloud.mall.infrastructure.tools.function;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author: 夜痕
 * @Date: 2022-02-03 4:02 下午
 * @Description:
 */
@Component
public class SimpleFunction {

    @Bean
   public Function<List<String>, Boolean> validateParamNotBlank() {
        return params -> params.stream().filter(StrUtil::isAllNotBlank)
            .anyMatch(StrUtil::isNotBlank);
    }

    @Bean
    public Function<List<Long>, Boolean> validateNumValueLegal() {
        return params -> !params.stream()
            .anyMatch(param -> Objects.isNull(param) || param == 0L);
    }

    @Bean
    public Function<String, String> toUpperCase() {
        return value -> value.toUpperCase();
    }

    /**
     * md5 加密
     */
    @Bean
    public Function<String, String> encryptCode() {
        return plainText -> new String(DigestUtil.md5(plainText, CharsetUtil.UTF_8));
    }

}
