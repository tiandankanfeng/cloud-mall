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
 * @Author: ε€η
 * @Date: 2022-02-07 4:21 δΈε
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
            Lists.newArrayList(goodsDO.getId()))) {
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
                // θ·εζ­€ζ η­Ύ hits
                final List<StatisticsDO> statisticsDOS = this.statisticsWrapper.queryByParamAndOrderByHits(new StatisticsDO()
                    .setUserId(userId)
                    .setTag(tag));

                statisticsDOS.stream()
                    .forEach(statisticsDO -> {
                        final Long hits = statisticsDO.getHits();
                        hitsSum.updateAndGet(v -> v + hits);
                        limitShowLoad.putIfAbsent(tag, 0L);
                        limitShowLoad.put(tag, limitShowLoad.get(tag) + hits);
                    });

            });

        limitShowLoad.forEach((k, v) -> {
            final double limitLoad = (double)v / hitsSum.get();
            final List<GoodsDO> currentTagsGoods = Lists.newArrayList();
            // ζ¨‘η³εΉιε­ε¨εΉιδΈε°ηζε΅(ε ζ­€δ»₯ 10000δΈΊεΊζ°εΊε±εζ―θΏθ‘ 1000ηζζ―θΏθ‘θ?‘η?)
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

        // θΏζ»€ε»ι
        return res.stream()
            .distinct()
            .collect(Collectors.toList());
    }

    @Override
    public List<GoodsDO> distinctGoodsInfo(final String distinctParam) {
        // ζ Ήζ?η¨ζ·ζ η­ΎθΏθ‘η­ιζεΊ
        final List<GoodsDO> goodsDOList = this.goodsWrapper.distinctSearchGoodsInfo(distinctParam);

        if (Objects.nonNull(SessionUtil.currentSession())) {
            final Long userId = SessionUtil.currentSession().getUserId();
            final UserDO userDO = this.userWrapper.queryByUserId(userId);

            if (Objects.nonNull(userDO)) {
                final List<String> tagsList = Splitter.on(",")
                    .splitToList(userDO.getTags());
                // order list
                // εεζ η­Ύε½δΈ­η¨ζ·θεθΆε€θζεΊι ε
                final List<GoodsDO> resOrderedList = goodsDOList.stream()
                    .sorted((s1, s2) -> this.calculateHit(tagsList, s2.getTags()) - this.calculateHit(tagsList,
                        s1.getTags()))
                    // ε»ι
                    .distinct()
                    .collect(Collectors.toList());

                goodsDOList.clear();
                // ζ·»ε ζε?εΊεηεεδΏ‘ζ―
                goodsDOList.addAll(resOrderedList);
            }
        }

        // θθε°η¨ζ·ε―θ½η»ε½δΊδ½ζ­€ζΆθεε°ζͺθ’«ζε»Ίζθζ―ζ Ήζ?ζ η­Ύη­ιεΊζ₯ηεεζ°ιζ―θΎε°
        if (CollectionUtils.isEmpty(goodsDOList) && goodsDOList.size() < 200) {
            // ιζΊθ―»εεεδΏ‘ζ―
            List<GoodsDO> goodsDOS = goodsWrapper.queryGoodsByParam(null);
            goodsDOS.stream()
                .filter(goodsDO -> !goodsDOList.contains(goodsDO))
                .forEach(goodsDO -> {
                    goodsDOList.add(goodsDO);
                });
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
