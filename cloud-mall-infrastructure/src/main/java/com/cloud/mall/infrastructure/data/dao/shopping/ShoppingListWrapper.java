package com.cloud.mall.infrastructure.data.dao.shopping;

import com.cloud.mall.infrastructure.dataObject.workbench.shopping.ShoppingListDO;

/**
 * @Author: 夜痕
 * @Date: 2022-02-07 5:10 下午
 * @Description:
 */
public interface ShoppingListWrapper {

    /**
     * 购物车添加新的商品
     * @param shoppingListEntity
     */
    void addNewGoodsIntoList(ShoppingListDO shoppingListEntity);

    /**
     * 从购物车中移除商品
     * @param shoppingListId
     */
    void removeGoodsFromList(Long shoppingListId);

    /**
     * 对购物车中商品数量的更改
     * @param shoppingListId
     * @param quantity
     */
    void updateGoodsInfoFromList(Long shoppingListId, Long quantity);
}
