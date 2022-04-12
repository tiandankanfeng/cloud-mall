package com.cloud.mall.infrastructure.dataObject.workbench.goods;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.mall.infrastructure.dataObject.SequenceBaseDO;
import lombok.Data;

/**
 * @Author: 夜痕
 * @Date: 2022-02-05 7:01 下午
 * @Description: 商品信息描述, 一件商品其实就是对应一类.
 * 将商品限制在二级类目上
 */
@TableName("goods")
@Data
public class GoodsDO extends SequenceBaseDO {
    private static final long serialVersionUID = -4241856163434779492L;

    /**
     * 商品名称
     */
    private String tradeName;

    /**
     * 商品相关描述
     */
    private String description;

    /**
     * 商品相关图片信息 urls
     * 一张作为主展示, 两张左右衬托即可.
     * JSON
     */
    private String url;

    /**
     * 一级类目
     */
    private String cate1;
    /**
     * 二级类目
     */
    private String cate2;

    /**
     * 商品价格
     */
    private BigDecimal amount;

    /**
     * 商品上所有标签
     * 一件商品上可能存在多个标签
     * 匹配时进行模糊匹配
     */
    private String tags;

    /**
     * 商品归属商家 Id
     */
    private Long userId;

    /**
     * 商品归属商家 nick
     */
    private String userNick;

    /**
     * 商品库存数量
     */
    private Long quantity;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * 验重
     * @param o
     * @return
     */
    @Override
    public boolean equals(final Object o) {
        final GoodsDO goodsDO = (GoodsDO)o;
        return this.getId().equals(goodsDO.getId());
    }
}
