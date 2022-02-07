package com.cloud.mall.implementation.shopping;

import java.util.Objects;

import com.cloud.mall.domain.workbench.goods.model.GoodsVO;
import com.cloud.mall.domain.workbench.shopping.ShoppingListDomainService;
import com.cloud.mall.infrastructure.data.dao.shopping.ShoppingListWrapper;
import com.cloud.mall.infrastructure.dataObject.workbench.shopping.ShoppingListDO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 夜痕
 * @Date: 2022-02-07 5:35 下午
 * @Description:
 */
@Service
public class ShoppingListDomainServiceImpl implements ShoppingListDomainService {
    @Autowired
    private ShoppingListWrapper shoppingListWrapper;

    @Override
    public void addOrUpdateGoodsInList(final GoodsVO goodsVO) {
        final ShoppingListDO shoppingListDO = new ShoppingListDO();
        BeanUtils.copyProperties(goodsVO, shoppingListDO);

        if (0L != shoppingListDO.getId() && Objects.isNull(shoppingListDO.getId())) { // 对购物车中商品数量更新
            this.shoppingListWrapper.updateGoodsInfoFromList(shoppingListDO.getUserId(), shoppingListDO.getQuantity());
        } else { // 购物车新增商品
            this.shoppingListWrapper.addNewGoodsIntoList(shoppingListDO);
        }
    }
}
