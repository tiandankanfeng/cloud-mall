package com.cloud.mall.infrastructure.data.dao.client;

import java.util.List;

import com.cloud.mall.infrastructure.dataObject.workbench.ClientDO;

/**
 * @Author: 夜痕
 * @Date: 2022-01-23 1:25 下午
 * @Description:
 */
public interface ClientWrapper {
    /**
     * 查询所有数据, 限制 1000
     * @return
     */
    List<ClientDO> queryAllData();
}
