package com.cloud.mall.domain.workbench.cates;

import java.util.List;

import com.cloud.mall.domain.workbench.cates.model.Cate1sVO;
import com.cloud.mall.domain.workbench.cates.model.CatesVO;
import com.cloud.mall.infrastructure.dataObject.workbench.cate.CatesDO;

/**
 * @Author: 夜痕
 * @Date: 2022-02-08 11:51 下午
 * @Description:
 */
public interface CatesDomainService {

    /**
     * 添加或更新类目
     * @param catesEntity
     */
    void addOrUpdateCates(CatesDO catesEntity);

    /**
     * 条件匹配删除类目信息
     * @param catesParam
     */
    void deleteCatesByParam(CatesDO catesParam);

    /**
     * 显示所有一级类目信息
     * @return
     */
    List<Cate1sVO> showAllCate1sInfo();

    /**
     * 显示所有类目信息
     * @return
     */
    CatesVO showAllCatesInfo();
}
