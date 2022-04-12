package com.cloud.mall.infrastructure.data.dao.goods.impl;

import java.util.List;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cloud.mall.infrastructure.data.dao.goods.GoodsWrapper;
import com.cloud.mall.infrastructure.dataObject.workbench.goods.GoodsDO;
import com.cloud.mall.infrastructure.mapper.GoodsMapper;
import com.cloud.mall.infrastructure.utils.SessionUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 夜痕
 * @Date: 2022-02-06 3:14 下午
 * @Description:
 */
@Service
public class GoodsWrapperImpl implements GoodsWrapper {
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public void insertGoodsRecord(final GoodsDO goodsEntity) {
        goodsEntity.setUserId(SessionUtil.currentSession().getUserId());
        this.goodsMapper.insert(goodsEntity);
    }

    @Override
    public void updateGoodsInfoById(final GoodsDO goodsEntity) {
        if (Objects.isNull(goodsEntity)) {
            return;
        }
        this.goodsMapper.updateById(goodsEntity);
    }

    @Override
    public List<GoodsDO> queryGoodsByParam(final GoodsDO goodsParam) {
        if (Objects.isNull(goodsParam)) {
            return Lists.newArrayList();
        }

        final LambdaQueryWrapper<GoodsDO> lambdaQuery = Wrappers.<GoodsDO>lambdaQuery(goodsParam);
        return this.goodsMapper.selectList(lambdaQuery);
    }

    @Override
    public void deleteGoodsById(final Long id) {
        this.goodsMapper.deleteById(id);
    }

    @Override
    public List<GoodsDO> queryGoodsByTagsFuzzySearch(final String tag, final Double limitLoad) {
        // 默认显示 10000 条
        final int limits = (int)(10000 * limitLoad);
        final LambdaQueryWrapper<GoodsDO> lambdaWrapper = Wrappers.<GoodsDO>lambdaQuery()
            .like(GoodsDO::getTags, '%' + tag + '%')
            .last("limit " + limits);
        return this.goodsMapper.selectList(lambdaWrapper);
    }

    @Override
    public GoodsDO queryGoodsByPrimaryId(final Long goodsId) {
        return this.goodsMapper.selectById(goodsId);
    }

    @Override
    public List<GoodsDO> distinctSearchGoodsInfo(final String distinctParam) {
        final LambdaQueryWrapper<GoodsDO> lambdaWrapper = Wrappers.<GoodsDO>lambdaQuery()
            .like(GoodsDO::getTradeName, distinctParam);
        return this.goodsMapper.selectList(lambdaWrapper);
    }
}
