package com.cloud.mall.infrastructure.dataObject;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @Author: 夜痕
 * @Date: 2022-01-19 9:09 下午
 * @Description:
 */
@Data
public class SequenceBaseDO implements Serializable {

    private static final long serialVersionUID = -4629578409600643918L;

    private Long id;

    private String createNick;

    private Date gmtCreate;

    private String modifiedNick;

    private Date gmtModified;
}
