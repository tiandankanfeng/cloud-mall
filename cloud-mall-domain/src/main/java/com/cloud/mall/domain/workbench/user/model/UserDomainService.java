package com.cloud.mall.domain.workbench.user.model;

import java.io.IOException;
import java.util.List;

import com.cloud.mall.infrastructure.dataObject.workbench.user.UserDO;
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
     * 用户进行登陆认证
     * @param account
     * @param pswd
     * @param mobile
     * @return
     */
    Long userAuthentication(String account, String pswd, String mobile);

    /**
     * 用户上传、更新头像
     * @param file
     * @param userId
     * @return
     * @throws IOException
     */
    String uploadHeadImg(MultipartFile file, Long userId) throws IOException;

    /**
     * 用户进行手机号码的绑定
     * @param account
     * @param mobile
     * @return
     */
    Boolean userBindMobile(String account, String mobile);

    /**
     * 用户验证手机号码
     * @param account
     * @param msgCode
     * @return
     */
    Boolean validateMobileMsgCode(String account, String msgCode);

    /**
     * 更新用户信息
     * @param userDO
     */
    void updateUserInfo(UserDO userDO);

    /**
     * 根据统计数据更新用户标签后将最新标签返回
     * 这是本项目的关键核心
     * all algorithm would be here.
     * @param userId
     * @return
     */
    List<String> updateUserTagsByStatistics(Long userId);

}
