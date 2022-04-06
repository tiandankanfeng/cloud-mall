package com.cloud.mall.infrastructure.data.dao.statistics.impl;

import java.util.List;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cloud.mall.infrastructure.data.dao.statistics.StatisticsWrapper;
import com.cloud.mall.infrastructure.dataObject.workbench.statistics.StatisticsDO;
import com.cloud.mall.infrastructure.mapper.StatisticsMapper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 夜痕
 * @Date: 2022-02-07 9:06 下午
 * @Description:
 */
@Service
public class StatisticsWrapperImpl implements StatisticsWrapper {
    @Autowired
    private StatisticsMapper statisticsMapper;

    @Override
    public void updateUserInterestTags(final StatisticsDO statisticsDO) {
        this.statisticsMapper.updateById(statisticsDO);
    }

    @Override
    public void insertStatisticsRecord(final StatisticsDO statisticsEntity) {
        this.statisticsMapper.insert(statisticsEntity);
    }

    @Override
    public List<StatisticsDO> queryByParamAndOrderByHits(final StatisticsDO statisticsParam) {
        if (Objects.isNull(statisticsParam)) {
            return Lists.newArrayList();
        }


        final LambdaQueryWrapper<StatisticsDO> lambdaQuery = Wrappers.<StatisticsDO>lambdaQuery(
                statisticsParam)
            .orderByDesc(StatisticsDO::getHits);
        return this.statisticsMapper.selectList(lambdaQuery);
    }

    @Override
    public List<StatisticsDO> queryByUserId(final Long userId) {
        final LambdaQueryWrapper<StatisticsDO> lambdaWrapper = Wrappers.<StatisticsDO>lambdaQuery()
            .eq(StatisticsDO::getUserId, userId);
        return this.statisticsMapper.selectList(lambdaWrapper);
    }
}
