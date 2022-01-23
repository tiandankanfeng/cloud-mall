package com.cloud.mall.app;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.cloud.mall.*"})
@MapperScan("com.cloud.mall.infrastructure.mapper")
public class CloudMallAppApplication implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(CloudMallAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("[run] 获得数据源:{}", dataSource.getClass());
    }
}
