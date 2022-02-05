package com.cloud.mall.infrastructure.data.dao.msg;

import java.util.List;

import com.cloud.mall.infrastructure.dataObject.workbench.msg.MsgRecordDO;

/**
 * @Author: 夜痕
 * @Date: 2022-02-05 7:19 下午
 * @Description:
 */
public interface MsgRecordWrapper {

    /**
     * 添加消息记录
     * @param msgRecordEntity
     */
    void insertRecord(MsgRecordDO msgRecordEntity);

    /**
     * 查询发送给指定用户的消息记录
     * @param userId
     * @return
     */
    List<MsgRecordDO> queryMsgRecordByUserId(Long userId);
}
