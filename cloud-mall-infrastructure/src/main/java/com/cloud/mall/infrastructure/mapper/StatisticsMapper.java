package com.cloud.mall.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.mall.infrastructure.dataObject.workbench.statistics.StatisticsDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: 夜痕
 * @Date: 2022-02-07 9:05 下午
 * @Description:
 */
@Mapper
public interface StatisticsMapper extends BaseMapper<StatisticsDO> {
}
