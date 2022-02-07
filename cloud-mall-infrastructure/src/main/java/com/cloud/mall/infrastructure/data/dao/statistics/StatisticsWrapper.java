package com.cloud.mall.infrastructure.data.dao.statistics;

import java.util.List;

import com.cloud.mall.infrastructure.dataObject.workbench.statistics.StatisticsDO;

/**
 * @Author: 夜痕
 * @Date: 2022-02-07 9:06 下午
 * @Description:
 */
public interface StatisticsWrapper {

    /**
     * 更新统计信息
     * @param statisticsDO
     */
    void updateUserInterestTags(StatisticsDO statisticsDO);

    /**
     * 新增记录
     * @param statisticsEntity
     */
    void insertStatisticsRecord(StatisticsDO statisticsEntity);

    /**
     * 条件搜索
     * @param statisticsParam
     * @return
     */
    List<StatisticsDO> queryByParam(StatisticsDO statisticsParam);
}
