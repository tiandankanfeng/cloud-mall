package com.cloud.mall.infrastructure;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.alibaba.druid.filter.config.ConfigTools;

import cn.hutool.core.util.StrUtil;
import com.cloud.mall.domain.workbench.msg.model.MsgCodeEnum;
import com.cloud.mall.infrastructure.utils.MsgSendUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@Slf4j
@RunWith(JUnit4.class)
class CloudMallInfrastructureApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void testDbOnEncrypt() throws Exception {
        System.out.println("hello world");
        val userName = "Wu913428!";
        final String[] keyPair = ConfigTools.genKeyPair(512);
        // 私钥
        val privateKey = keyPair[0];
        // 公钥
        val publicKey = keyPair[1];
        // 加密
        final var encryptCode = ConfigTools.encrypt(privateKey, userName);
        final var decryptCode = ConfigTools.decrypt(publicKey, encryptCode);
        System.out.println(StrUtil.format("私钥:{}, 加密后:{}", privateKey, encryptCode));
        System.out.println(StrUtil.format("公钥:{}, 解密后:{}", publicKey, decryptCode));

        ConfigTools.main("Wu913428!".split(" "));
        System.out.println("wu913428".split(" ")[0]);
    }

    @Test
    public void testMsgSend() {
        final Future<MsgCodeEnum> future = MsgSendUtil.sendMsg("19855143393", "good night!");
        try {
            // doSomething
            final MsgCodeEnum msgCodeEnum = future.get();
            System.out.println(msgCodeEnum);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        } catch (final ExecutionException e) {
            e.printStackTrace();
        }
    }


}
