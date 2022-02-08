package com.cloud.mall.domain.workbench.goods;

import java.util.List;

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

    /**
     * 根据用户已有 tags显示感兴趣的标签
     * @param userId
     * @return
     */
    List<GoodsDO> showUserInterestGoodsByKnownTags(Long userId);

    /**
     * 模糊匹配商品信息
     * @param distinctParam
     * @return
     */
    List<GoodsDO> distinctGoodsInfo(String distinctParam);
}
