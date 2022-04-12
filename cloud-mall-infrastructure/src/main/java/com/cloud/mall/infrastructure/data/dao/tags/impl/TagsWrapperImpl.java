package com.cloud.mall.infrastructure.data.dao.tags.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cloud.mall.infrastructure.data.dao.tags.TagsWrapper;
import com.cloud.mall.infrastructure.dataObject.workbench.tags.TagsDO;
import com.cloud.mall.infrastructure.mapper.TagsMapper;
import groovy.transform.AutoImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 远道
 * @Date: 2022-04-11 9:09 下午
 * @Description:
 */
@Service
public class TagsWrapperImpl implements TagsWrapper {

    @Autowired
    private TagsMapper tagsMapper;

    @Override
    public List<TagsDO> getAllTags() {
        final LambdaQueryWrapper<TagsDO> lambdaWrapper = Wrappers.<TagsDO>lambdaQuery(null);
        return this.tagsMapper.selectList(lambdaWrapper);
    }

    @Override
    public void addNewTag(final TagsDO tagsDO) {
        this.tagsMapper.insert(tagsDO);
    }
}
