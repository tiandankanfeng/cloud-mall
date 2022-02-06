package com.cloud.mall.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.mall.infrastructure.dataObject.workbench.cate.CatesDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: 夜痕
 * @Date: 2022-02-06 3:05 下午
 * @Description:
 */
@Mapper
public interface CatesMapper extends BaseMapper<CatesDO> {
}
