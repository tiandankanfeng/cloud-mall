package com.cloud.mall.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.cloud.mall.*"})
public class CloudMallAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudMallAppApplication.class, args);
    }

}
