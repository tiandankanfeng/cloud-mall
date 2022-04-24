package com.cloud.mall.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: 夜痕
 * @Date: 2022-02-03 3:08 下午
 * @Description: 处理跨域请求
 */
@Configuration
public class CrosConfig {

    public static final String[] ORIGINS = new String[]{"GET", "POST", "PUT", "DELETE"};

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOriginPatterns("*")
                    .allowedMethods("*")
                    .allowedHeaders("*")
                    .allowCredentials(true);

            }
        };
    }

    //@Bean
    //public WebMvcConfigurer corsConfigurer() {
    //    return new CorsConfigurer();
    //}
    //
    //public static class CorsConfigurer implements WebMvcConfigurer {
    //    @Override
    //    public void addCorsMappings(final CorsRegistry registry) {
    //        registry.addMapping("/**")
    //            .allowedOrigins("*")
    //            .allowedMethods("*")
    //            .allowedHeaders("*")
    //            .allowCredentials(true);
    //    }
    //}
}