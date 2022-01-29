package com.cloud.mall.infrastructure.data.dao.user;

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
}
