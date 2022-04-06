package com.cloud.mall.infrastructure.dataObject.workbench.statistics;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.mall.infrastructure.dataObject.SequenceBaseDO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: 夜痕
 * @Date: 2022-02-07 8:58 下午
 * @Description:
 */
@Accessors(chain = true)
@Data
@TableName("statistics")
public class StatisticsDO extends SequenceBaseDO {
    private static final long serialVersionUID = -6692185963157095615L;

    private Long userId;

    /**
     * 标签
     */
    private String tag;

    /**
     * 二级类目
     */
    private Long cate2Code;

    /**
     * 点击数
     */
    private Long hits;
}
