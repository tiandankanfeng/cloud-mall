package com.cloud.mall.infrastructure.data.dao.shopping.impl;

import java.util.List;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cloud.mall.infrastructure.data.dao.shopping.ShoppingListWrapper;
import com.cloud.mall.infrastructure.dataObject.workbench.shopping.ShoppingListDO;
import com.cloud.mall.infrastructure.mapper.ShoppingListMapper;
import com.google.common.collect.Lists;
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

    @Override
    public List<ShoppingListDO> queryByParam(final ShoppingListDO shoppingListParam) {
        if (Objects.isNull(shoppingListParam)) {
            return Lists.newArrayList();
        }

        final LambdaQueryWrapper<ShoppingListDO> lambdaQuery = Wrappers.<ShoppingListDO>lambdaQuery(
            shoppingListParam);

        return this.shoppingListMapper.selectList(lambdaQuery);
    }
}
