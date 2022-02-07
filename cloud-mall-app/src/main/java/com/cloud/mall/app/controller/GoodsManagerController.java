package com.cloud.mall.app.controller;

import java.util.Objects;

import com.cloud.mall.app.aop.annotaion.PortalSessionAnnotation;
import com.cloud.mall.domain.workbench.goods.model.GoodsVO;
import com.cloud.mall.domain.workbench.goods.GoodsDomainService;
import com.cloud.mall.domain.workbench.shopping.ShoppingListDomainService;
import com.cloud.mall.infrastructure.data.dao.goods.GoodsWrapper;
import com.cloud.mall.infrastructure.dataObject.workbench.goods.GoodsDO;
import com.cloud.mall.infrastructure.result.exp.BizException;
import com.cloud.mall.infrastructure.result.exp.BizExceptionProperties;
import com.cloud.mall.infrastructure.tools.function.SimpleFunction;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 夜痕
 * @Date: 2022-02-07 4:10 下午
 * @Description:
 */
@Api(tags = "商品管理器")
@RequestMapping("/goodsManager")
@RestController
public class GoodsManagerController {
    @Autowired
    private GoodsDomainService goodsDomainService;
    @Autowired
    private ShoppingListDomainService shoppingListDomainService;
    @Autowired
    private SimpleFunction simpleFunction;
    @Autowired
    private GoodsWrapper goodsWrapper;
    

    @ApiOperation("商家发布或更新商品")
    @PostMapping("/publishGoods")
    @PortalSessionAnnotation(expectedUserAdmin = true)
    public void publishGoods(@RequestBody final GoodsDO goodsDO) {
        if (Objects.isNull(goodsDO)) {
            throw new BizException(BizExceptionProperties.PARAM_VALIDATE_NOT_PASS.getMsg());
        }

        this.goodsDomainService.publishGoods(goodsDO);
    }

    @ApiOperation("商家下架商品")
    @PutMapping("/goodsOffShelf/{id}")
    @PortalSessionAnnotation(expectedUserAdmin = true)
    public void goodsOffShelf(@PathVariable("id") final Long id) {
        if (!this.simpleFunction.validateNumValueLegal().apply(Lists.newArrayList(id))) {
            throw new BizException(BizExceptionProperties.PARAM_VALIDATE_NOT_PASS.getMsg());
        }

        this.goodsWrapper.deleteGoodsById(id);
    }

    @ApiOperation(("购物车添加新的商品"))
    @PutMapping("/addNewGoodsIntoList")
    @PortalSessionAnnotation
    public void addNewGoodsIntoList(@RequestBody final GoodsVO goodsVO) {
        if (Objects.isNull(goodsVO)) {
            throw new BizException(BizExceptionProperties.PARAM_VALIDATE_NOT_PASS.getMsg());
        }

        this.shoppingListDomainService.addOrUpdateGoodsInList(goodsVO);
    }
}
