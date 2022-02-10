package com.cloud.mall.infrastructure.data.dao.cates.impl;

import java.util.List;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cloud.mall.infrastructure.data.dao.cates.CatesWrapper;
import com.cloud.mall.infrastructure.dataObject.workbench.cate.CatesDO;
import com.cloud.mall.infrastructure.mapper.CatesMapper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 夜痕
 * @Date: 2022-02-08 11:35 下午
 * @Description:
 */
@Service
public class CateWrapperImpl implements CatesWrapper {
    @Autowired
    private CatesMapper catesMapper;

    @Override
    public void addCates(final CatesDO catesEntity) {
        this.catesMapper.insert(catesEntity);
    }

    @Override
    public void deleteCates(final CatesDO catesParam) {
        final LambdaQueryWrapper<CatesDO> lambdaWrapper = Wrappers.<CatesDO>lambdaQuery(catesParam);
        this.catesMapper.delete(lambdaWrapper);
    }

    @Override
    public List<CatesDO> queryByParam(final CatesDO catesParam) {
        if (Objects.isNull(catesParam)) {
            return Lists.newArrayList();
        }
        final LambdaQueryWrapper<CatesDO> lambdaWrapper = Wrappers.<CatesDO>lambdaQuery(catesParam);
        return this.catesMapper.selectList(lambdaWrapper);
    }

    @Override
    public List<CatesDO> queryAllCate1Entities() {
        final QueryWrapper<CatesDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.groupBy("cate1_code", "cate1_desc")
            .select("cate1_code", "cate1_desc");
        return this.catesMapper.selectList(queryWrapper);
    }

    @Override
    public void updateCatesInfo(final CatesDO catesDO) {
        this.catesMapper.updateById(catesDO);
    }

    @Override
    public List<CatesDO> queryAllCatesInfo() {
        final LambdaQueryWrapper<CatesDO> lambdaWrapper = Wrappers.<CatesDO>lambdaQuery(null);
        return this.catesMapper.selectList(lambdaWrapper);
    }
}
