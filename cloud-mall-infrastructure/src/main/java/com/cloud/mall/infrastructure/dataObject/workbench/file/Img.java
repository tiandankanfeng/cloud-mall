package com.cloud.mall.infrastructure.dataObject.workbench.file;

import java.util.Arrays;
import java.util.Optional;

import cn.hutool.core.util.StrUtil;

/**
 * @author liangye
 * 定义允许上传的图片后缀
 */
public enum Img {
    JPG("jpg"),
    PNG("png"),
    JPEG("jpeg");

    public final String desc;

    Img(final String desc) {
        this.desc = desc;
    }

    /**
     * 根据文件类型寻找对应枚举
     * @param type
     * @return
     */
    public static Optional<Img> getImgEnumByType(final String type) {
        if (StrUtil.isBlank(type)) {
            return Optional.empty();
        }
        return Optional.of(Arrays.stream(Img.values())
            .filter(img -> img.desc.equals(type))
            .findFirst()
            .orElse(null));
    }
}
