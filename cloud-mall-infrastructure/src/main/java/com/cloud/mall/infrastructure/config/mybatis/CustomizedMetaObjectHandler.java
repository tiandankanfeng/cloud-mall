package com.cloud.mall.infrastructure.config.mybatis;

import java.util.Date;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
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

    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue(gmtCreate, new Date());
        metaObject.setValue(gmtModified, new Date());
        // todo, 登录态提取
    }

    /**
     * metaData 自动更新, 不允许手动更新
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue(gmtModified, new Date());
        // todo, 提取登录态
    }
}
