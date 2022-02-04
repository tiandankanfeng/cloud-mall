package com.cloud.mall.infrastructure.data.dao.user.impl;

import java.util.List;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cloud.mall.infrastructure.data.dao.user.UserWrapper;
import com.cloud.mall.infrastructure.dataObject.workbench.user.UserDO;
import com.cloud.mall.infrastructure.mapper.UserMapper;
import com.google.common.collect.Lists;
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

    @Override
    public List<UserDO> queryByUserParam(final UserDO userParam) {
        if (Objects.isNull(userParam)) {
            return Lists.newArrayList();
        }

        final LambdaQueryWrapper<UserDO> lambdaQuery = Wrappers.<UserDO>lambdaQuery(userParam);
        return this.userMapper.selectList(lambdaQuery);
    }

    @Override
    public void insertUserRecord(final UserDO userEntity) {
        this.userMapper.insert(userEntity);
    }

    @Override
    public void updateUserInfo(final UserDO userEntity) {
        this.userMapper.updateById(userEntity);
    }
}
