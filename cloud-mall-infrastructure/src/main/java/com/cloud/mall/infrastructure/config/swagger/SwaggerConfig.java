package com.cloud.mall.infrastructure.config.swagger;

import java.util.ArrayList;

import cn.hutool.core.text.StrBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: 夜痕
 * @Date: 2022-01-24 12:11 上午
 * @Description: 开启 swagger
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${server.port}")
    private int port;

    @Bean
    public Docket docket(Environment environment) {
        System.out.println("swagger bean！");
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
            .groupName("Cloud-Mall")
            .enable(true)
            .select()
            .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
            .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("Cloud-Mall", "https://github.com/tiandankanfeng/cloud-mall", "2252578955@qq.com");
        /**
         * 配置Swagger文件信息
         */
        return new ApiInfo(
            "Cloud-Mall",
            "start our mall!",
            "v1.0",
            "https://liangye-xo.xyz",
            contact,
            "online-doc",
            StrBuilder.create("http://localhost:").append(port).append("/doc.html").toString(),
            new ArrayList()
        );
    }
}
