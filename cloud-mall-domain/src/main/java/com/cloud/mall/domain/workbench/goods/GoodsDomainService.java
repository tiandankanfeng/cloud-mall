package com.cloud.mall.domain.workbench.goods;

import com.cloud.mall.infrastructure.dataObject.workbench.goods.GoodsDO;

/**
 * @Author: 夜痕
 * @Date: 2022-02-07 4:20 下午
 * @Description:
 */
public interface GoodsDomainService {

    /**
     * 商家发布或更新商品信息
     * @param goodsDO
     * @return
     */
    void publishGoods(GoodsDO goodsDO);
}
