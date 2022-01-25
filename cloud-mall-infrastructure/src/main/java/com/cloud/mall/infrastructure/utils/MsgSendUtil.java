package com.cloud.mall.infrastructure.utils;

import java.io.IOException;

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

    public static Integer sendMsg(final String receivePhoneNumber, final String content) {
        final HttpClient client = new HttpClient();
        final PostMethod post = new PostMethod(SMSConfigProperties.smsUrl);

        MsgSendUtil.log.info("uid:{}, key:{}, smsUrl:{}", SMSConfigProperties.uid, SMSConfigProperties.key, SMSConfigProperties.smsUrl);
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

        try {
            // todo, 这块更改成异步
            client.executeMethod(post);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        // 获取返回相关信息  --> 判断状态码
        final int statusCode = post.getStatusCode();
        // todo, 记录短信发送.
        MsgSendUtil.log.info("message send, phone:{}, statusCode:{}", receivePhoneNumber, statusCode);

        String result = "";
        try {
            result = new String(post.getResponseBodyAsString().getBytes("gbk"));
        } catch (final Exception e) {
            e.printStackTrace();
        }

        post.releaseConnection(); // 非群发消息

        return Integer.parseInt(result);
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
