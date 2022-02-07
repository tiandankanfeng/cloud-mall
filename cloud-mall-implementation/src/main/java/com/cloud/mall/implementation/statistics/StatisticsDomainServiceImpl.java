package com.cloud.mall.implementation.statistics;

import java.util.List;
import java.util.Objects;

import com.alibaba.fastjson.JSON;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.cloud.mall.domain.workbench.statistics.StatisticsDomainService;
import com.cloud.mall.infrastructure.data.dao.goods.GoodsWrapper;
import com.cloud.mall.infrastructure.data.dao.shopping.ShoppingListWrapper;
import com.cloud.mall.infrastructure.data.dao.statistics.StatisticsWrapper;
import com.cloud.mall.infrastructure.dataObject.workbench.goods.GoodsDO;
import com.cloud.mall.infrastructure.dataObject.workbench.shopping.ShoppingListDO;
import com.cloud.mall.infrastructure.dataObject.workbench.statistics.StatisticsDO;
import com.google.common.base.Splitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 夜痕
 * @Date: 2022-02-07 9:21 下午
 * @Description:
 */
@Service
public class StatisticsDomainServiceImpl implements StatisticsDomainService {
    @Autowired
    private StatisticsWrapper statisticsWrapper;
    @Autowired
    private GoodsWrapper goodsWrapper;
    @Autowired
    private ShoppingListWrapper shoppingListWrapper;

    @Override
    public void statisticsUserClickOnPages(final Long userId, final Long goodsId) {
        final GoodsDO goodsDO = this.goodsWrapper.queryGoodsByPrimaryId(goodsId);
        if (Objects.nonNull(goodsDO)) { // insert
            Splitter.on(",")
                .splitToList(goodsDO.getTags())
                .stream()
                .forEach(tag -> {
                    final StatisticsDO statisticsDO = new StatisticsDO()
                        .setUserId(userId)
                        .setTag(tag);
                    final List<StatisticsDO> statisticsDOS = this.statisticsWrapper.queryByParam(statisticsDO);

                    if (CollectionUtils.isNotEmpty(statisticsDOS)) { // snapshot update
                        Long hits = statisticsDO.getHits();
                        statisticsDO.setHits(++hits);
                        this.statisticsWrapper.updateUserInterestTags(statisticsDO);
                    } else { // insert
                        final StatisticsDO statisticsEntity = new StatisticsDO()
                            .setUserId(userId)
                            .setHits(1L)
                            .setTag(tag);
                        this.statisticsWrapper.updateUserInterestTags(statisticsEntity);
                    }

                });
        }
    }

    @Override
    public void statisticsShoppingLists(final Long userId) {
        final List<ShoppingListDO> shoppingListDOS = this.shoppingListWrapper.queryByParam(
            new ShoppingListDO()
                .setUserId(userId));

        if (CollectionUtils.isNotEmpty(shoppingListDOS)) {
            shoppingListDOS.stream()
                .forEach(shoppingListDO -> {
                    final GoodsDO goodsDO = JSON.parseObject(String.valueOf(shoppingListDO),
                        GoodsDO.class);
                    this.statisticsUserClickOnPages(userId, goodsDO.getId());
                });
        }
    }
}
