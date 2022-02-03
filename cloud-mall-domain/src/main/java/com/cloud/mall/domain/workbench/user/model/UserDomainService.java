package com.cloud.mall.domain.workbench.user.model;

/**
 * @Author: 夜痕
 * @Date: 2022-02-03 3:14 下午
 * @Description: 用户相关服务
 */
public interface UserDomainService {

    /**
     * 返回用户标识 id
     * @param account
     * @param pswd
     * @return
     */
    Long authenticationUserLogin(String account, String pswd);

    /**
     * 用户进行注册认证
     * @param account
     * @param pswd
     * @return
     */
    Long userAuthentication(String account, String pswd);
}
