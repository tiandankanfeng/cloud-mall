package com.cloud.mall.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.mall.infrastructure.dataObject.workbench.tags.TagsDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: 夜痕
 * @Date: 2022-02-06 3:06 下午
 * @Description:
 */
@Mapper
public interface TagsMapper extends BaseMapper<TagsDO> {
}
