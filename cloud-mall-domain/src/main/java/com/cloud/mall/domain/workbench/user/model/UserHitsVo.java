package com.cloud.mall.domain.workbench.user.model;

import java.io.Serializable;

import com.cloud.mall.domain.workbench.cates.model.CatesVO;
import lombok.Data;

/**
 * @Author: 远道
 * @Date: 2022-04-06 4:29 下午
 * @Description:
 */
@Data
public class UserHitsVo implements Serializable {

    private static final long serialVersionUID = 2748656495898215320L;

    private Long userId;

    private String userNick;

    /**
     * 统计行为
     */
    private CatesVO catesVO;
}

