package com.cloud.mall.infrastructure.dataObject.workbench;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.mall.infrastructure.dataObject.SequenceBaseDO;
import lombok.Data;

/**
 * @Author: 夜痕
 * @Date: 2022-01-23 1:16 下午
 * @Description:
 */
@Data
@TableName("client")
public class ClientDO extends SequenceBaseDO {

    private String name;

    private Integer age;

}
