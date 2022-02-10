package com.cloud.mall.domain.workbench.cates.model;

import java.util.HashMap;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 夜痕
 * @Date: 2022-02-10 12:43 上午
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CatesVO {
    /**
     * 根据一级类目进行划分显示所有类目信息
     */
    private HashMap<Cate1sVO, List<Cate2sVO>> catesVO;
}
