package com.cloud.mall.infrastructure.dataObject.workbench.cate;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.mall.infrastructure.dataObject.SequenceBaseDO;
import lombok.Data;

/**
 * @Author: 夜痕
 * @Date: 2022-02-06 2:59 下午
 * @Description: 类目事实枚举表
 */
@Data
@TableName("cates")
public class CatesDO extends SequenceBaseDO {
    private static final long serialVersionUID = -6813932352483789282L;

    /**
     * 类目一标识
     */
    private String cate1Code;

    /**
     * 类目一描述
     */
    private String cate1Desc;
    /**
     * 类目二标识
     */
    private String cate2Code;

    /**
     * 类目二描述
     */
    private String cate2Desc;

}
