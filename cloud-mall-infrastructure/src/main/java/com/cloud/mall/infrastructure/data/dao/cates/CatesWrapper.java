package com.cloud.mall.infrastructure.data.dao.cates;

import java.util.List;

import com.cloud.mall.infrastructure.dataObject.workbench.cate.CatesDO;

/**
 * @Author: 夜痕
 * @Date: 2022-02-08 11:31 下午
 * @Description:
 */
public interface CatesWrapper {

    /**
     * 新增类目
     * @param catesEntity
     */
    void addCates(CatesDO catesEntity);

    /**
     * 条件匹配删除类目
     * @param catesParam
     */
    void deleteCates(CatesDO catesParam);

    /**
     * 条件匹配类目
     * @param catesParam
     * @return
     */
    List<CatesDO> queryByParam(CatesDO catesParam);

    /**
     * 查询所有以及类目
     * @return
     */
    List<CatesDO> queryAllCate1Entities();

    /**
     * 更新类目信息
     * @param catesDO
     */
    void updateCatesInfo(CatesDO catesDO);
}
