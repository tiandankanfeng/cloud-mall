package com.cloud.mall.implementation.cates;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.cloud.mall.domain.workbench.cates.CatesDomainService;
import com.cloud.mall.domain.workbench.cates.model.Cate1sVO;
import com.cloud.mall.domain.workbench.cates.model.Cate2sVO;
import com.cloud.mall.domain.workbench.cates.model.CatesVO;
import com.cloud.mall.infrastructure.data.dao.cates.CatesWrapper;
import com.cloud.mall.infrastructure.dataObject.workbench.cate.CatesDO;
import com.cloud.mall.infrastructure.tools.function.SimpleFunction;
import com.google.common.collect.Lists;
import lombok.val;
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

    @Override
    public CatesVO showAllCatesInfo() {
        final List<CatesDO> catesDOList = this.catesWrapper.queryAllCatesInfo();
        if (CollectionUtils.isEmpty(catesDOList)) {
            return new CatesVO();
        }

        val resMap = new HashMap<Cate1sVO, List<Cate2sVO>>();

        catesDOList.stream()
            // 分组
            .collect(Collectors.groupingBy(CatesDO::getCate1Code))
            .forEach((k, v) -> {
                resMap.put(Cate1sVO.builder()
                    .cate1_code(v.get(0).getCate1Code())
                    .cate1_desc(v.get(0).getCate1Desc())
                    .build(),
                    v.stream()
                        .map(value -> Cate2sVO.builder()
                            .cate2_code(value.getCate2Code())
                            .cate2_desc(value.getCate2Desc())
                            .build())
                        .collect(Collectors.toList())
                );
            });

        return CatesVO.builder()
            .catesVO(resMap)
            .build();

    }

    @Override
    public CatesDO upperFindCate1ByCate2(final Long cate2) {
        final List<CatesDO> catesDOS = this.catesWrapper.queryByParam(new CatesDO().setCate2Code(String.valueOf(cate2)));
        return CollectionUtils.isEmpty(catesDOS) ? null : catesDOS.get(0);
    }
}
