package com.cloud.mall.implementation.goods;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.cloud.mall.domain.workbench.goods.GoodsDomainService;
import com.cloud.mall.infrastructure.data.dao.goods.GoodsWrapper;
import com.cloud.mall.infrastructure.data.dao.statistics.StatisticsWrapper;
import com.cloud.mall.infrastructure.data.dao.user.UserWrapper;
import com.cloud.mall.infrastructure.dataObject.workbench.goods.GoodsDO;
import com.cloud.mall.infrastructure.dataObject.workbench.statistics.StatisticsDO;
import com.cloud.mall.infrastructure.dataObject.workbench.user.UserDO;
import com.cloud.mall.infrastructure.tools.function.SimpleFunction;
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
    @Autowired
    private StatisticsWrapper statisticsWrapper;
    @Autowired
    private SimpleFunction simpleFunction;

    @Override
    public void publishGoods(final GoodsDO goodsDO) {
        this.doInsertOrUpdateGoodsInfo(goodsDO);
    }

    private void doInsertOrUpdateGoodsInfo(final GoodsDO goodsDO) {
        if (Objects.nonNull(goodsDO) && this.simpleFunction.validateNumValueLegal().apply(
            Lists.newArrayList(goodsDO.getUserId()))) {
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
        val limitShowLoad = new HashMap<String, Long>();
        val hitsSum = new AtomicLong();
        Splitter.on(",")
            .splitToList(userDO.getTags())
            .stream()
            .forEach(tag -> {
                // 获取此标签 hits
                final List<StatisticsDO> statisticsDOS = this.statisticsWrapper.queryByParamAndOrderByHits(new StatisticsDO()
                    .setUserId(userId)
                    .setTag(tag));
                if (CollectionUtils.isNotEmpty(statisticsDOS)) {
                    final Long hits = statisticsDOS.get(0).getHits();
                    hitsSum.updateAndGet(v -> v + hits);
                    limitShowLoad.put(tag, hits);
                }

            });

        limitShowLoad.forEach((k, v) -> {
            final double limitLoad = (double)(v / hitsSum.get());
            final List<GoodsDO> currentTagsGoods = Lists.newArrayList();
            // 模糊匹配存在匹配不到的情况(因此以 10000为基数底层则是进行 1000的权比进行计算)
            final List<GoodsDO> goodsDOS = this.goodsWrapper.queryGoodsByTagsFuzzySearch(k, limitLoad);


            goodsDOS.stream()
                .forEach(goodsDO -> {
                    if (currentTagsGoods.size() >= (int)(1000 * limitLoad)) {
                        return;
                    }
                    final boolean goodsTagsList = Splitter.on(",")
                        .splitToList(goodsDO.getTags())
                        .contains(k);
                    if (goodsTagsList) {
                        currentTagsGoods.add(goodsDO);
                    }
                });

            if (CollectionUtils.isNotEmpty(currentTagsGoods)) {
                res.addAll(currentTagsGoods);
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
