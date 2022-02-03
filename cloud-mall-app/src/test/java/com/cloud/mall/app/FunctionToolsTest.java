package com.cloud.mall.app;

import com.cloud.mall.infrastructure.tools.function.SimpleFunction;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: 夜痕
 * @Date: 2022-02-03 4:11 下午
 * @Description:
 */
@SpringBootTest
public class FunctionToolsTest {
    @Autowired
    private SimpleFunction simpleFunction;

    @Test
    public void testValidateParamNotBlank() {
        System.out.println(this.simpleFunction.validateParamNotBlank().apply(Lists.list(null, "", "yuandao")));
    }
}
