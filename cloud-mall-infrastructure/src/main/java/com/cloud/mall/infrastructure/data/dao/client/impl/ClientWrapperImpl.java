package com.cloud.mall.infrastructure.data.dao.client.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cloud.mall.infrastructure.data.dao.client.ClientWrapper;
import com.cloud.mall.infrastructure.dataObject.workbench.ClientDO;
import com.cloud.mall.infrastructure.mapper.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 夜痕
 * @Date: 2022-01-23 1:27 下午
 * @Description:
 */
@Service
public class ClientWrapperImpl implements ClientWrapper {

    @Autowired
    private ClientMapper clientMapper;
    @Override
    public List<ClientDO> queryAllData() {
        LambdaQueryWrapper<ClientDO> queryWrapper = Wrappers.<ClientDO>lambdaQuery(null)
            .last("limit 1000");
        return clientMapper.selectList(queryWrapper);
    }
}
