package com.cloud.mall.domain.workbench.goods.model;

import lombok.Data;

/**
 * @Author: 夜痕
 * @Date: 2022-02-07 5:27 下午
 * @Description:
 */
@Data
public class GoodsVO {
    /**
     * 购物车标识
     */
    private Long id;
    /**
     * 谁的购物车
     */
    private Long userId;

    /**
     * 添加哪个商品
     */
    private Long goodsId;

    /**
     * 添加的商品数量是多少
     */
    private Long quantity;
}
