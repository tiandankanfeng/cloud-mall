package com.cloud.mall.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.mall.infrastructure.dataObject.workbench.user.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: 夜痕
 * @Date: 2022-01-26 2:19 下午
 * @Description:
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
}
