package com.cloud.mall.infrastructure.config.mybatis;

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
        //todo
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //todo
    }
}
