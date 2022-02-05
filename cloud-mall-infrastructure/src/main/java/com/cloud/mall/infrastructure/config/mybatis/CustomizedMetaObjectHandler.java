package com.cloud.mall.infrastructure.config.mybatis;

import java.util.Date;
import java.util.Objects;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.cloud.mall.infrastructure.utils.SessionUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @Author: 夜痕
 * @Date: 2022-01-19 9:03 下午
 * @Description: 元数据填充
 */
@Component
public class CustomizedMetaObjectHandler implements MetaObjectHandler {

    private static final String gmtCreate = "gmtCreate";

    private static final String createNick = "createNick";

    private static final String gmtModified = "gmtModified";

    private static final String modifiedNick = "modifiedNick";

    private static final String isDeleted = "isDeleted";

    @Override
    public void insertFill(final MetaObject metaObject) {
        metaObject.setValue(CustomizedMetaObjectHandler.gmtCreate, new Date());
        metaObject.setValue(CustomizedMetaObjectHandler.gmtModified, new Date());
        metaObject.setValue(CustomizedMetaObjectHandler.isDeleted, 0);
        // 提取 session信息
        if (Objects.nonNull(SessionUtil.currentSession())) {
            metaObject.setValue(CustomizedMetaObjectHandler.createNick, SessionUtil.currentSession().getUserNick());
            metaObject.setValue(CustomizedMetaObjectHandler.modifiedNick, SessionUtil.currentSession().getUserNick());
        }
    }

    /**
     * metaData 自动更新, 不允许手动更新
     *
     * @param metaObject
     */
    @Override
    public void updateFill(final MetaObject metaObject) {
        metaObject.setValue(CustomizedMetaObjectHandler.gmtModified, new Date());
        metaObject.setValue(CustomizedMetaObjectHandler.modifiedNick, SessionUtil.currentSession().getUserNick());
    }
}
