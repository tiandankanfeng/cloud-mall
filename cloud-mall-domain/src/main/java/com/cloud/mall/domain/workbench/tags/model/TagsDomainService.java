package com.cloud.mall.domain.workbench.tags.model;

import java.util.List;

import com.cloud.mall.infrastructure.dataObject.workbench.tags.TagsDO;

/**
 * @Author: 远道
 * @Date: 2022-04-11 9:05 下午
 * @Description:
 */
public interface TagsDomainService {

    /**
     * 展示所有标签
     * @return
     */
    List<TagsDO> getAllTags();

    /**
     * 获取所有的标签类别
     * @return
     */
    List<String> getAllTagsType();
}
