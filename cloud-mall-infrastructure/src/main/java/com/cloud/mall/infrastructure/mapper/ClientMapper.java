package com.cloud.mall.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.mall.infrastructure.dataObject.workbench.ClientDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: 夜痕
 * @Date: 2022-01-23 1:22 下午
 * @Description:
 */
@Mapper
public interface ClientMapper extends BaseMapper<ClientDO> {
}
