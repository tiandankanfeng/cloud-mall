package com.cloud.mall.infrastructure.dataObject;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * @Author: ε€η
 * @Date: 2022-01-19 9:09 δΈε
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

    @TableLogic(value = "0", delval = "1")
    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;
}
