package com.cloud.mall.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.mall.infrastructure.dataObject.workbench.msg.MsgRecordDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: 夜痕
 * @Date: 2022-02-05 7:21 下午
 * @Description:
 */
@Mapper
public interface MsgRecordMapper extends BaseMapper<MsgRecordDO> {
}
