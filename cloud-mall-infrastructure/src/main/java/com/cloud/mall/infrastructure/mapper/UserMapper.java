package com.cloud.mall.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.mall.infrastructure.dataObject.workbench.user.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: ε€η
 * @Date: 2022-01-26 2:19 δΈε
 * @Description:
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
}
