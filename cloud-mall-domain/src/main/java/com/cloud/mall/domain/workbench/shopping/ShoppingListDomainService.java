package com.cloud.mall.domain.workbench.shopping;

import com.cloud.mall.domain.workbench.goods.model.GoodsVO;

/**
 * @Author: 夜痕
 * @Date: 2022-02-07 5:32 下午
 * @Description:
 */
public interface ShoppingListDomainService {

    /**
     * 购物车添加或更新商品
     * @param goodsVO
     */
    void addOrUpdateGoodsInList(GoodsVO goodsVO);
}
