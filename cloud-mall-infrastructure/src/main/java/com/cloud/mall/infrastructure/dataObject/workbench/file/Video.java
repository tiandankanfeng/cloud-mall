package com.cloud.mall.infrastructure.dataObject.workbench.file;

import java.util.Arrays;
import java.util.Optional;

import cn.hutool.core.util.StrUtil;

/**
 * @author liangye
 */
public enum Video {
    avi("avi"),
    // wmv格式
    wmv("wmv"),
    // mpeg格式
    mpeg("mpeg"),
    // mp4格式
    mp4("mp4"),
    // m4v格式
    m4v("m4v"),
    // mov格式
    mov("mov"),
    // asf格式
    asf("asf"),
    // flv格式
    flv("flv"),
    // f4v格式
    f4v("f4v"),
    // rmvb格式
    rmvb("rmvb"),
    // rm格式
    rm("em"),
    // vob格式
    vob("vob"),
    // webm格式
    webm("webm");

    public final String desc;

    Video(final String desc) {
        this.desc = desc;
    }

    /**
     * 根据文件类型寻找对应枚举
     * @param type
     * @return
     */
    public static Optional<Video> getImgEnumByType(final String type) {
        if (StrUtil.isBlank(type)) {
            return Optional.empty();
        }

        return Optional.of(Arrays.stream(Video.values())
            .filter(video -> video.desc.equals(type))
            .findFirst()
            .orElse(null));
    }
}
