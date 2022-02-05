package com.cloud.mall.infrastructure.dataObject.workbench.msg;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.mall.infrastructure.dataObject.SequenceBaseDO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: 夜痕
 * @Date: 2022-02-05 7:03 下午
 * @Description:
 */
@Data
@Accessors(chain = true)
@TableName("msg_record")
public class MsgRecordDO extends SequenceBaseDO {
    private static final long serialVersionUID = -5729834605353070503L;

    private Long userId;

    private String userNick;

    private String mobile;

    private String content;

    /**
     * 短信是否发送成功
     */
    private Boolean isSuccess;
}
