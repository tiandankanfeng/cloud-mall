package com.cloud.mall.infrastructure.dataObject.workbench.tags;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.mall.infrastructure.dataObject.SequenceBaseDO;
import lombok.Data;

/**
 * @Author: 夜痕
 * @Date: 2022-02-06 2:33 下午
 * @Description: 标签事实枚举表
 */
@TableName("tags")
@Data
public class TagsDO extends SequenceBaseDO {
    private static final long serialVersionUID = 3988486469086588351L;

    private Integer TagType;

    /**
     * 标签相关描述
     */
    private String description;
}
