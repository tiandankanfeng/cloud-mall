package com.cloud.mall.implementation.goods;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.cloud.mall.domain.workbench.goods.GoodsDomainService;
import com.cloud.mall.infrastructure.data.dao.goods.GoodsWrapper;
import com.cloud.mall.infrastructure.data.dao.user.UserWrapper;
import com.cloud.mall.infrastructure.dataObject.workbench.goods.GoodsDO;
import com.cloud.mall.infrastructure.dataObject.workbench.user.UserDO;
import com.cloud.mall.infrastructure.utils.SessionUtil;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 夜痕
 * @Date: 2022-02-07 4:21 下午
 * @Description:
 */
@Service
public class GoodsDomainServiceImpl implements GoodsDomainService {
    @Autowired
    private GoodsWrapper goodsWrapper;
    @Autowired
    private UserWrapper userWrapper;

    @Override
    public void publishGoods(final GoodsDO goodsDO) {
        this.doInsertOrUpdateGoodsInfo(goodsDO);
    }

    private void doInsertOrUpdateGoodsInfo(final GoodsDO goodsDO) {
        if (0L != goodsDO.getUserId() && Objects.nonNull(goodsDO)) {
            this.goodsWrapper.updateGoodsInfoById(goodsDO);
        } else {
            this.goodsWrapper.insertGoodsRecord(goodsDO);
        }
    }

    @Override
    public List<GoodsDO> showUserInterestGoodsByKnownTags(final Long userId) {
        final UserDO userDO = this.userWrapper.queryByUserId(userId);
        if (Objects.isNull(userDO)) {
            return Lists.newArrayList();
        }

        final List<GoodsDO> res = Lists.newArrayList();
        Splitter.on(",")
            .splitToList(userDO.getTags())
            .stream()
            .forEach(tag -> {
                // 标签匹配商品
                final List<GoodsDO> goodsDOS = this.goodsWrapper.queryGoodsByTagsFuzzySearch(tag);
                if (CollectionUtils.isNotEmpty(goodsDOS)) {
                    res.addAll(goodsDOS);
                }
            });

        return res;
    }

    @Override
    public List<GoodsDO> distinctGoodsInfo(final String distinctParam) {
        // todo, 根据用户标签进行筛选排序(待验证)
        final List<GoodsDO> goodsDOList = this.goodsWrapper.distinctSearchGoodsInfo(distinctParam);

        if (Objects.nonNull(SessionUtil.currentSession())) {
            final Long userId = SessionUtil.currentSession().getUserId();
            final UserDO userDO = this.userWrapper.queryByUserId(userId);

            if (Objects.nonNull(userDO)) {
                final List<String> tagsList = Splitter.on(",")
                    .splitToList(userDO.getTags());
                // order list
                // 商品标签命中用户肖像越多者排序靠前
                final List<GoodsDO> resOrderedList = goodsDOList.stream()
                    .sorted((s1, s2) -> this.calculateHit(tagsList, s2.getTags()) - this.calculateHit(tagsList,
                        s1.getTags()))
                    .collect(Collectors.toList());
                return resOrderedList;
            }
        }

        return goodsDOList;

    }

    private int calculateHit(final List<String> userTags, final String goodsTags) {
        final HashSet<String> hitsSet = new HashSet<>(userTags);
        final List<String> s1Tags = Splitter.on(",")
            .splitToList(goodsTags);
        hitsSet.addAll(s1Tags);
        return hitsSet.size() - (userTags.size() + s1Tags.size());
    }
}
