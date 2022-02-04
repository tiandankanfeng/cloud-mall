package com.cloud.mall.infrastructure.dataObject;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * @Author: 夜痕
 * @Date: 2022-01-19 9:09 下午
 * @Description:
 */
@Data
//@SuperBuilder(toBuilder = true)
public class SequenceBaseDO implements Serializable {

    private static final long serialVersionUID = -4629578409600643918L;

    private Long id;

    @TableField(fill = FieldFill.INSERT)
    private String createNick;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String modifiedNick;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}
