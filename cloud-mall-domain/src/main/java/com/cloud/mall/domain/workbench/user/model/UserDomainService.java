package com.cloud.mall.domain.workbench.user.model;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 用户上传、更新头像
     * @param file
     * @param userId
     * @return
     * @throws IOException
     */
    String uploadHeadImg(MultipartFile file, Long userId) throws IOException;
}
