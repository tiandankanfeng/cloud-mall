package com.cloud.mall.infrastructure.dataObject.workbench;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.mall.infrastructure.dataObject.SequenceBaseDO;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @Author: 夜痕
 * @Date: 2022-01-23 1:16 下午
 * @Description:
 */
@Data
//@SuperBuilder(toBuilder = true)
@TableName("client")
public class ClientDO extends SequenceBaseDO {

    private static final long serialVersionUID = 5134808783598127826L;

    private String name;

    private Integer age;

}
