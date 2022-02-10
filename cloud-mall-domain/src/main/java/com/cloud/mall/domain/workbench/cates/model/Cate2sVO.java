package com.cloud.mall.domain.workbench.cates.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 夜痕
 * @Date: 2022-02-10 12:59 上午
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cate2sVO {

    private String cate2_code;

    private String cate2_desc;
}
