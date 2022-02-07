package com.cloud.mall.domain.workbench.statistics;

/**
 * @Author: 夜痕
 * @Date: 2022-02-07 9:21 下午
 * @Description:
 */
public interface StatisticsDomainService {

    /**
     * 统计用户在页面上的统计数
     * @param userId
     * @param goodsId
     */
    void statisticsUserClickOnPages(Long userId, Long goodsId);

    /**
     * 统计用户购物车
     * @param userId
     */
    void statisticsShoppingLists(Long userId);
}
