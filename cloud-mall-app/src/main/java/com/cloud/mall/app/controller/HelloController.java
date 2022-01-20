package com.cloud.mall.app.controller;

import com.cloud.mall.domain.workbench.hello.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 夜痕
 * @Date: 2022-01-19 1:32 下午
 * @Description:
 */
@RequestMapping("/app/test")
@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    @GetMapping("/hello")
    public String testOnHello() {
        return helloService.hello();
    }
}
