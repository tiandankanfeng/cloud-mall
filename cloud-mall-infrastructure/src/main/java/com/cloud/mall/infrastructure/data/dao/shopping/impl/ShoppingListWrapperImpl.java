package com.cloud.mall.infrastructure.data.dao.shopping.impl;

import com.cloud.mall.infrastructure.data.dao.shopping.ShoppingListWrapper;
import com.cloud.mall.infrastructure.dataObject.workbench.shopping.ShoppingListDO;
import com.cloud.mall.infrastructure.mapper.ShoppingListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 夜痕
 * @Date: 2022-02-07 5:15 下午
 * @Description:
 */
@Service
public class ShoppingListWrapperImpl implements ShoppingListWrapper {
    @Autowired
    private ShoppingListMapper shoppingListMapper;

    @Override
    public void addNewGoodsIntoList(final ShoppingListDO shoppingListEntity) {
        this.shoppingListMapper.insert(shoppingListEntity);
    }

    @Override
    public void removeGoodsFromList(final Long shoppingListId) {
        this.shoppingListMapper.deleteById(shoppingListId);
    }

    @Override
    public void updateGoodsInfoFromList(final Long shoppingListId, final Long quantity) {
        final ShoppingListDO shoppingListDO = new ShoppingListDO();
        shoppingListDO.setId(shoppingListId);
        shoppingListDO.setQuantity(quantity);

        this.shoppingListMapper.updateById(shoppingListDO);
    }
}
