package com.cloud.mall.infrastructure.utils;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import com.cloud.mall.infrastructure.dataObject.workbench.msg.MsgCodeEnum;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * @Author: 夜痕
 * @Date: 2022-01-25 7:13 下午
 * @Description:
 */
@Slf4j
@UtilityClass
public class MsgSendUtil {

    private static final ExecutorService executorService;

    static {
        executorService = new ThreadPoolExecutor(20, 50, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1024),
            new ThreadFactoryBuilder().setNameFormat("msg-snd-thread-%d").setDaemon(Boolean.TRUE).build());
    }

    /**
     * 仅提供消息发送能力
     * 对于消息记录需要在外界根据自身需求进行
     *
     * @param receivePhoneNumber
     * @param content
     * @return
     */
    public static Future<MsgCodeEnum> sendMsg(final String receivePhoneNumber, final String content) {
        final PostMethod post = new PostMethod(SMSConfigProperties.smsUrl);

        MsgSendUtil.log.info("uid:{}, key:{}, smsUrl:{}", SMSConfigProperties.uid, SMSConfigProperties.key,
            SMSConfigProperties.smsUrl);
        // 设置转码
        post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");

        //设置相关参数
        final NameValuePair[] data = {
            new NameValuePair("Uid", SMSConfigProperties.uid),
            new NameValuePair("Key", SMSConfigProperties.key),
            new NameValuePair("smsMob", receivePhoneNumber),
            new NameValuePair("smsText", content)
        };

        post.setRequestBody(data);
        return MsgSendUtil.doSndMsg(post, receivePhoneNumber);
    }

    private Future<MsgCodeEnum> doSndMsg(final PostMethod post, final String receivePhoneNumber) {
        final HttpClient client = new HttpClient();
        // 解析获取手机号码
        return CompletableFuture.supplyAsync(() -> {
            try {
                client.executeMethod(post);
            } catch (final IOException e) {
                e.printStackTrace();
            }

            // 获取返回相关信息  --> 判断状态码
            final int statusCode = post.getStatusCode();
            MsgSendUtil.log.info("[良夜的博客, 测试]message send, phone:{}, statusCode:{}", receivePhoneNumber, statusCode);

            post.releaseConnection(); // 非群发消息
            return MsgCodeEnum.getMsgEnumByCode(statusCode);
        });
    }

    /**
     * SMS平台配置
     */
    @UtilityClass
    private
    class SMSConfigProperties {
        private final String uid = "liangye";

        private final String key = "d41d8cd98f00b204e980";

        private final String smsUrl = "http://sms.webchinese.cn/web_api/";
    }
}
