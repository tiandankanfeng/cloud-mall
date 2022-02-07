package com.cloud.mall.implementation.goods;

import java.util.Objects;

import com.cloud.mall.domain.workbench.goods.GoodsDomainService;
import com.cloud.mall.infrastructure.data.dao.goods.GoodsWrapper;
import com.cloud.mall.infrastructure.dataObject.workbench.goods.GoodsDO;
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


}
