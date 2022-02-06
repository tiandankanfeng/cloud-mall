package com.cloud.mall.infrastructure.data.dao.goods;

import java.util.List;

import com.cloud.mall.infrastructure.dataObject.workbench.goods.GoodsDO;

/**
 * @Author: 夜痕
 * @Date: 2022-02-06 3:12 下午
 * @Description:
 */
public interface GoodsWrapper {

    /**
     * 添加商品
     * @param goodsEntity
     */
    void insertGoodsRecord(GoodsDO goodsEntity);

    /**
     * 更新商品信息
     * @param goodsEntity
     */
    void updateGoodsInfoById(GoodsDO goodsEntity);

    /**
     * 条件获取商品信息
     * @param goodsParam
     * @return
     */
    List<GoodsDO> queryGoodsByParam(GoodsDO goodsParam);
}
