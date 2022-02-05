package com.cloud.mall.infrastructure.data.dao.msg.impl;

import java.util.List;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cloud.mall.infrastructure.data.dao.msg.MsgRecordWrapper;
import com.cloud.mall.infrastructure.dataObject.workbench.msg.MsgRecordDO;
import com.cloud.mall.infrastructure.mapper.MsgRecordMapper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 夜痕
 * @Date: 2022-02-05 7:20 下午
 * @Description:
 */
@Service
public class MsgRecordWrapperImpl implements MsgRecordWrapper {
    @Autowired
    private MsgRecordMapper msgRecordMapper;

    @Override
    public void insertRecord(final MsgRecordDO msgRecordEntity) {
        this.msgRecordMapper.insert(msgRecordEntity);
    }

    @Override
    public List<MsgRecordDO> queryMsgRecordByUserId(final Long userId) {
        if (0L == userId || Objects.isNull(userId)) {
            return Lists.newArrayList();
        }

        final LambdaQueryWrapper<MsgRecordDO> lambdaWrapper = Wrappers.<MsgRecordDO>lambdaQuery()
            .eq(MsgRecordDO::getUserId, userId);

        return this.msgRecordMapper.selectList(lambdaWrapper);
    }
}
