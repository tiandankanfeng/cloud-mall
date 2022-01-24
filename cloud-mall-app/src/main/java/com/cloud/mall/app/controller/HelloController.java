package com.cloud.mall.app.controller;

import java.util.List;

import com.cloud.mall.domain.workbench.hello.HelloService;
import com.cloud.mall.infrastructure.data.dao.client.ClientWrapper;
import com.cloud.mall.infrastructure.dataObject.workbench.ClientDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 夜痕
 * @Date: 2022-01-19 1:32 下午
 * @Description:
 */
@Api(tags = "测试控制器")
@RequestMapping("/app/test")
@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;
    @Autowired
    private ClientWrapper clientWrapper;

    @ApiOperation("hello")
    @GetMapping("/hello")
    public String testOnHello() {
        return helloService.hello();
    }

    /**
     * 仅测试下这么写, 不规范写法
     * @return
     */
    @ApiOperation("测试数据库连接")
    @GetMapping("/getAllClient")
    public List<ClientDO> getAllData() {
        return clientWrapper.queryAllData();
    }

}
