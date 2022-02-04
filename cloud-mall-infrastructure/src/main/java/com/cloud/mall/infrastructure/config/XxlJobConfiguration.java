package com.cloud.mall.infrastructure.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 夜痕
 * @Date: 2022-02-04 4:05 下午
 * @Description: 配置 xxl-job
 */
@Configuration
public class XxlJobConfiguration {

    @Value("${xxl.job.admin.address}")
    private String address;
    @Value("${xxl.job.executor.appName}")
    private String appName;
    @Value("${xxl.job.executor.ip}")
    private String ip;
    @Value("${xxl.job.executor.port}")
    private int port;
    @Value("${xxl.job.accessToken}")
    private String accessToken;
    @Value("${xxl.job.executor.logPath}")
    private String logPath;
    @Value("${xxl.job.executor.logRetensionDays}")
    private int logRetensionDays;

    @Bean
    public XxlJobSpringExecutor xxlJobSpringExecutor() {
        System.out.println("address:" + this.address);
        // 创建执行器
        final XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(this.address);
        xxlJobSpringExecutor.setAppname(this.appName);
        xxlJobSpringExecutor.setIp(this.ip);
        xxlJobSpringExecutor.setPort(this.port);
        xxlJobSpringExecutor.setAccessToken(this.accessToken);
        xxlJobSpringExecutor.setLogPath(this.logPath);
        xxlJobSpringExecutor.setLogRetentionDays(this.logRetensionDays);
        // 返回
        return xxlJobSpringExecutor;
    }
}
