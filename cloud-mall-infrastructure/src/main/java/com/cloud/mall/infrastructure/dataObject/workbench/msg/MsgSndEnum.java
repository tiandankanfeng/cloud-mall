package com.cloud.mall.infrastructure.dataObject.workbench.msg;

import java.util.Arrays;
import java.util.Optional;

import cn.hutool.core.util.StrUtil;
import com.cloud.mall.infrastructure.dataObject.workbench.file.Img;

/**
 * @Author: 夜痕
 * @Date: 2022-02-05 7:35 下午
 * @Description: 发送短信缘由枚举
 */
public enum MsgSndEnum {
    USER_BIND_MSG("用户绑定手机号码"),
    USER_VALIDATE_ACCOUNT("用户验证账户");
    private final String desc;

    MsgSndEnum(final String desc) {
        this.desc = desc;
    }

    public static Optional<MsgSndEnum> getMsgSndEnumByType(final String type) {
        if (StrUtil.isBlank(type)) {
            return Optional.empty();
        }
        return Optional.of(Arrays.stream(MsgSndEnum.values())
            .filter(msgSndEnum -> msgSndEnum.desc.equals(type))
            .findFirst()
            .orElse(null));
    }

}
