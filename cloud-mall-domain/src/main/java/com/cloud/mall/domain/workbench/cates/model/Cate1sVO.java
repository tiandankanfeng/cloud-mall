package com.cloud.mall.domain.workbench.cates.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 夜痕
 * @Date: 2022-02-09 12:08 上午
 * @Description: 一级类目范畴
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cate1sVO {

    private String cate1_code;

    private String cate1_desc;
}
