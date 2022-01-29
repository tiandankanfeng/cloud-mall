package com.cloud.mall.infrastructure.data.dao.user.impl;

import com.cloud.mall.infrastructure.data.dao.user.UserWrapper;
import com.cloud.mall.infrastructure.dataObject.workbench.user.UserDO;
import com.cloud.mall.infrastructure.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 夜痕
 * @Date: 2022-01-26 2:20 下午
 * @Description:
 */
@Service
public class UserWrapperImpl implements UserWrapper {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDO queryByUserId(final Long userId) {
        return this.userMapper.selectById(userId);
    }
}
