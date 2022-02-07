package com.cloud.mall.infrastructure.dataObject.workbench.shopping;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.mall.infrastructure.dataObject.SequenceBaseDO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: 夜痕
 * @Date: 2022-02-07 5:00 下午
 * @Description: 购物车上线 100
 */
@TableName("shopping_list")
@Data
@Accessors(chain = true)
public class ShoppingListDO extends SequenceBaseDO {
    private static final long serialVersionUID = 4565788386549438246L;
    /**
     * 谁的购物车
     */
    private Long userId;
    /**
     * 购物车中有啥
     */
    private Long goodsId;

    /**
     * 这啥又是啥
     * @ref GoodsDO
     */
    private String goodsInfo;

    /**
     * 商品数量
     */
    private Long quantity;
}
