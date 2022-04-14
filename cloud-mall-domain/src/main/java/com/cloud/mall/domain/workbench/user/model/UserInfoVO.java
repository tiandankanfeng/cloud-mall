package com.cloud.mall.domain.workbench.user.model;

import java.io.Serializable;

import com.cloud.mall.infrastructure.dataObject.workbench.user.SexEnum;
import com.cloud.mall.infrastructure.dataObject.workbench.user.UserIdentityEnum;
import lombok.Data;

/**
 * @Author: 远道
 * @Date: 2022-04-14 13:22
 * @Description:
 */
@Data
public class UserInfoVO implements Serializable {

    private static final long serialVersionUID = 9207016614220474479L;

    /**
     * 用户 id
     */
    private Long Id;

    /**
     * 账户, 即昵称
     */
    private String account;

    private Integer age;

    /**
     * 头像
     */
    private String headImg;

    private String email;

    private String mobile;

    /**
     * 用户角色
     */
    private UserIdentityEnum userIdentity;
    /**
     * 标签, 构建用户肖像
     */
    private String tags;
    /**
     * 性别:M、W
     */
    private SexEnum sex;

}
