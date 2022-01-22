package com.cloud.mall.implementation.hello;

import com.cloud.mall.domain.workbench.hello.HelloService;
import org.springframework.stereotype.Service;

/**
 * @Author: 夜痕
 * @Date: 2022-01-19 1:30 下午
 * @Description:
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello() {
        return "hello world!";
    }
}
