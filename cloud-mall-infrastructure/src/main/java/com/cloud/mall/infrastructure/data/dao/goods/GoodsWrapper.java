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

    /**
     * 唯一
     * @param goodsId
     * @return
     */
    GoodsDO queryGoodsByPrimaryId(Long goodsId);

    /**
     * 删除商品信息
     * @param id
     */
    void deleteGoodsById(Long id);

    /**
     * 根据标签模糊匹配商品
     * @param tag
     * @return
     */
    List<GoodsDO> queryGoodsByTagsFuzzySearch(String tag);

    /**
     * 模糊匹配商品信息
     * @param distinctParam
     * @return
     */
    List<GoodsDO> distinctSearchGoodsInfo(String distinctParam);
}
