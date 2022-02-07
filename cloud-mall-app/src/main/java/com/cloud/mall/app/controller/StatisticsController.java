package com.cloud.mall.app.controller;

import com.cloud.mall.app.aop.annotaion.PortalSessionAnnotation;
import com.cloud.mall.domain.workbench.statistics.StatisticsDomainService;
import com.cloud.mall.infrastructure.result.exp.BizException;
import com.cloud.mall.infrastructure.result.exp.BizExceptionProperties;
import com.cloud.mall.infrastructure.tools.function.SimpleFunction;
import com.cloud.mall.infrastructure.utils.SessionUtil;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 夜痕
 * @Date: 2022-02-07 8:48 下午
 * @Description: 项目真做起来的话这块估计是最吃性能的
 */
@Api(tags = "统计相关控制器(报表实现)")
@RequestMapping("/statistics")
@RestController
public class StatisticsController {
    @Autowired
    private SimpleFunction simpleFunction;
    @Autowired
    private StatisticsDomainService statisticsDomainService;

    @ApiOperation("统计用户在页面上对商品的点击")
    @PutMapping("/statisticsUserClickOnPages")
    @PortalSessionAnnotation
    public void statisticsUserClickOnPages(@PathVariable("goodsId") final Long goodsId) {
        if (!this.simpleFunction.validateNumValueLegal().apply(Lists.newArrayList(goodsId))) {
            throw new BizException(BizExceptionProperties.PARAM_VALIDATE_NOT_PASS.getMsg());
        }

        final Long userId = SessionUtil.currentSession().getUserId();

        this.statisticsDomainService.statisticsUserClickOnPages(userId, goodsId);
    }

    @ApiOperation("更新用户购物车统计信息")
    @PutMapping("/statisticsShoppingLists")
    @PortalSessionAnnotation
    public void statisticsShoppingLists() {
        final Long userId = SessionUtil.currentSession().getUserId();
        this.statisticsDomainService.statisticsShoppingLists(userId);
    }
}
