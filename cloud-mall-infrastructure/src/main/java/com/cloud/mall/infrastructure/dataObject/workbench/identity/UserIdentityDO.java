package com.cloud.mall.infrastructure.dataObject.workbench.identity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.mall.infrastructure.dataObject.SequenceBaseDO;
import lombok.Data;

/**
 * @Author: 夜痕
 * @Date: 2022-02-06 3:09 下午
 * @Description:
 */
@TableName("user_identity")
@Data
public class UserIdentityDO extends SequenceBaseDO {
    private static final long serialVersionUID = 7660256793863594132L;

    /**
     * 角色编码
     */
    private String identity_code;

    /**
     * 角色相关描述
     */
    private String identity_desc;
}
