package com.cloud.mall.app.controller;

import java.sql.ResultSet;
import java.util.List;
import java.util.Objects;

import com.cloud.mall.app.aop.annotaion.PortalInjectionAnnotation;
import com.cloud.mall.app.aop.annotaion.PortalSessionAnnotation;
import com.cloud.mall.domain.workbench.cates.CatesDomainService;
import com.cloud.mall.domain.workbench.cates.model.Cate1sVO;
import com.cloud.mall.domain.workbench.cates.model.CatesVO;
import com.cloud.mall.domain.workbench.goods.model.GoodsVO;
import com.cloud.mall.domain.workbench.goods.GoodsDomainService;
import com.cloud.mall.domain.workbench.shopping.ShoppingListDomainService;
import com.cloud.mall.domain.workbench.tags.model.TagEnum;
import com.cloud.mall.domain.workbench.tags.model.TagsDomainService;
import com.cloud.mall.infrastructure.data.dao.goods.GoodsWrapper;
import com.cloud.mall.infrastructure.data.dao.tags.TagsWrapper;
import com.cloud.mall.infrastructure.dataObject.workbench.cate.CatesDO;
import com.cloud.mall.infrastructure.dataObject.workbench.goods.GoodsDO;
import com.cloud.mall.infrastructure.dataObject.workbench.tags.TagsDO;
import com.cloud.mall.infrastructure.result.ResultDto;
import com.cloud.mall.infrastructure.result.exp.BizException;
import com.cloud.mall.infrastructure.result.exp.BizExceptionProperties;
import com.cloud.mall.infrastructure.tools.function.SimpleFunction;
import com.cloud.mall.infrastructure.utils.SessionUtil;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ??????
 * @Date: 2022-02-07 4:10 ??????
 * @Description:
 */
@Api(tags = "???????????????")
@RequestMapping("/goodsManager")
@RestController
public class GoodsManagerController {
    @Autowired
    private GoodsDomainService goodsDomainService;
    @Autowired
    private ShoppingListDomainService shoppingListDomainService;
    @Autowired
    private CatesDomainService catesDomainService;
    @Autowired
    private SimpleFunction simpleFunction;
    @Autowired
    private GoodsWrapper goodsWrapper;


    @ApiOperation("???????????????????????????")
    @PostMapping("/publishGoods")
    @PortalSessionAnnotation(expectedUserAdmin = true)
    public ResultDto<Void> publishGoods(@RequestBody final GoodsDO goodsDO) {
        if (Objects.isNull(goodsDO)) {
            throw new BizException(BizExceptionProperties.PARAM_VALIDATE_NOT_PASS.getMsg());
        }

        this.goodsDomainService.publishGoods(goodsDO);
        return new ResultDto<>();
    }

    @ApiOperation("??????????????????")
    @PutMapping("/goodsOffShelf/{id}")
    @PortalSessionAnnotation(expectedUserAdmin = true)
    public ResultDto<Void> goodsOffShelf(@PathVariable("id") final Long id) {
        if (!this.simpleFunction.validateNumValueLegal().apply(Lists.newArrayList(id))) {
            throw new BizException(BizExceptionProperties.PARAM_VALIDATE_NOT_PASS.getMsg());
        }

        this.goodsWrapper.deleteGoodsById(id);
        return new ResultDto<>();
    }

    @ApiOperation(("???????????????????????????"))
    @PutMapping("/addNewGoodsIntoList")
    @PortalSessionAnnotation
    public ResultDto<Void> addNewGoodsIntoList(@RequestBody final GoodsVO goodsVO) {
        if (Objects.isNull(goodsVO)) {
            throw new BizException(BizExceptionProperties.PARAM_VALIDATE_NOT_PASS.getMsg());
        }

        this.shoppingListDomainService.addOrUpdateGoodsInList(goodsVO);
        return new ResultDto<>();
    }

    @ApiOperation("??????????????????????????????????????????")
    @GetMapping("/showUserInterestGoodsByKnownTags")
    @PortalSessionAnnotation
    public ResultDto<List<GoodsDO>> showUserInterestGoodsByKnownTags() {
        final Long userId = SessionUtil.currentSession().getUserId();
        val resultDto = new ResultDto<List<GoodsDO>>();
        resultDto.setData(this.goodsDomainService.showUserInterestGoodsByKnownTags(userId));
        return resultDto;
    }

    @ApiOperation("/?????????????????????????????????????????? - ??????????????????????????????????????????")
    @GetMapping("/distinctSearchGoods")
    public ResultDto<List<GoodsDO>> distinctSearchGoods(final String distinctParam) {
        if (!this.simpleFunction.validateParamNotBlank().apply(Lists.newArrayList(distinctParam))) {
            throw new BizException(BizExceptionProperties.PARAM_VALIDATE_NOT_PASS.getMsg());
        }

        return new ResultDto(this.goodsDomainService.distinctGoodsInfo(distinctParam));
    }

    @ApiOperation("???????????????????????????")
    @PostMapping("/addOrUpdateCates")
    @PortalSessionAnnotation(expectedUserAdmin = true)
    public ResultDto<Void> addOrUpdateCates(@RequestBody final CatesDO catesEntity) {
        if (Objects.isNull(catesEntity)) {
            throw new BizException(BizExceptionProperties.PARAM_VALIDATE_NOT_PASS.getMsg());
        }

        this.catesDomainService.addOrUpdateCates(catesEntity);
        return new ResultDto<>();
    }

    @ApiOperation("??????????????????")
    @DeleteMapping("/deleteCates")
    @PortalSessionAnnotation
    public ResultDto<Void> deleteCates(@RequestBody final CatesDO catesParam) {
        if (Objects.isNull(catesParam)) {
            throw new BizException(BizExceptionProperties.PARAM_VALIDATE_NOT_PASS.getMsg());
        }

        this.catesDomainService.deleteCatesByParam(catesParam);
        return new ResultDto<>();
    }

    @ApiOperation("??????????????????????????????")
    @GetMapping("/showAllCate1sInfo")
    public ResultDto<Cate1sVO> showAllCate1sInfo() {
        return new ResultDto(this.catesDomainService.showAllCate1sInfo());
    }

    @ApiOperation("????????????????????????")
    @GetMapping("/showAllCatesInfo")
    public ResultDto<CatesVO> showAllCatesInfo() {
        return new ResultDto<>(this.catesDomainService.showAllCatesInfo());
    }

}


























