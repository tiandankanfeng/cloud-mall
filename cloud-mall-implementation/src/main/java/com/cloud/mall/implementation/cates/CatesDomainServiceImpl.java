package com.cloud.mall.implementation.cates;

import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.cloud.mall.domain.workbench.cates.CatesDomainService;
import com.cloud.mall.domain.workbench.cates.model.Cate1sVO;
import com.cloud.mall.infrastructure.data.dao.cates.CatesWrapper;
import com.cloud.mall.infrastructure.dataObject.workbench.cate.CatesDO;
import com.cloud.mall.infrastructure.tools.function.SimpleFunction;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 夜痕
 * @Date: 2022-02-08 11:53 下午
 * @Description:
 */
@Service
public class CatesDomainServiceImpl implements CatesDomainService {
    @Autowired
    private CatesWrapper catesWrapper;
    @Autowired
    private SimpleFunction simpleFunction;

    @Override
    public void addOrUpdateCates(final CatesDO catesEntity) {
        if (this.simpleFunction.validateNumValueLegal().apply(Lists.newArrayList(catesEntity.getId()))) { // update
            this.catesWrapper.updateCatesInfo(catesEntity);
        } else { // insert
            this.catesWrapper.addCates(catesEntity);
        }
    }

    @Override
    public void deleteCatesByParam(final CatesDO catesParam) {
        this.catesWrapper.deleteCates(catesParam);
    }

    @Override
    public List<Cate1sVO> showAllCate1sInfo() {
        final List<CatesDO> catesDOS = this.catesWrapper.queryAllCate1Entities();
        if (CollectionUtils.isNotEmpty(catesDOS)) {
            return catesDOS.stream()
                .map(catesDO -> Cate1sVO.builder()
                    .cate1_code(catesDO.getCate1Code())
                    .cate1_desc(catesDO.getCate1Desc())
                    .build())
                .collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }
}
