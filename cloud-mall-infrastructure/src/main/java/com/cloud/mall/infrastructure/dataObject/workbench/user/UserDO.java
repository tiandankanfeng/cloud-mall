package com.cloud.mall.infrastructure.dataObject.workbench.user;


import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.mall.infrastructure.dataObject.SequenceBaseDO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: 夜痕
 * @Date: 2022-01-25 1:04 下午
 * @Description: 用户表
 */
@Data

//@SuperBuilder(toBuilder = true)
@Accessors(chain = true)
@TableName("user")
public class UserDO extends SequenceBaseDO {

    private static final long serialVersionUID = -1920169541684607525L;

    /**
     * 账户, 即昵称
     */
    private String account;
    /**
     * 加盐
     */
    private String pswd;

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
