package com.cloud.mall.infrastructure.data.dao.user;

import java.util.List;

import com.cloud.mall.infrastructure.dataObject.workbench.user.UserDO;

/**
 * @Author: 夜痕
 * @Date: 2022-01-26 2:20 下午
 * @Description:
 */
public interface UserWrapper {
    /**
     * 根据 userId寻找用户信息
     * @param userId
     * @return
     */
    UserDO queryByUserId(Long userId);

    /**
     * 根据用户信息进行匹配
     * @param userParam
     * @return
     */
    List<UserDO> queryByUserParam(UserDO userParam);

    /**
     * 添加一条记录
     * @param userEntity
     * @return
     */
    void insertUserRecord(UserDO userEntity);

    /**
     * 更新用户信息
     * @param userEntity
     */
    void updateUserInfo(UserDO userEntity);
}
