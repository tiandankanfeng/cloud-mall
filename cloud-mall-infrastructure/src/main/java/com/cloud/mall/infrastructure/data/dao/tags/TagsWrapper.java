package com.cloud.mall.infrastructure.data.dao.tags;

import java.util.List;

import com.cloud.mall.infrastructure.dataObject.workbench.tags.TagsDO;

/**
 * @Author: 远道
 * @Date: 2022-04-11 9:09 下午
 * @Description:
 */
public interface TagsWrapper {

    /**
     * 获取所有标签
     * @return
     */
    List<TagsDO> getAllTags();

    /**
     * 添加新的标签
     * @param tagsDO
     */
    void addNewTag(TagsDO tagsDO);
}
