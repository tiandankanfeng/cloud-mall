package com.cloud.mall.infrastructure.session;

import java.io.Serializable;

import com.cloud.mall.domain.workbench.user.model.UserIdentityEnum;

/**
 * @Author: 夜痕
 * @Date: 2022-01-24 1:49 下午
 * @Description: 门户会话
 */
public class PortalSession implements Serializable {

    private static final long serialVersionUID = 8509172182941819045L;

    /**
     * 用户 nick
     */
    private String userNick;

    /**
     * 用户角色
     */
    private UserIdentityEnum userIdentityEnum;
}
