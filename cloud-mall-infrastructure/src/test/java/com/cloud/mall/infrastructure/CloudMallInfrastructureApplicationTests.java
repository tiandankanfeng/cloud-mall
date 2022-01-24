package com.cloud.mall.infrastructure;

import com.alibaba.druid.filter.config.ConfigTools;

import cn.hutool.core.util.StrUtil;
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
        val userName = "wu913428";
        String[] keyPair = ConfigTools.genKeyPair(512);
        // 私钥
        val privateKey = keyPair[0];
        // 公钥
        val publicKey = keyPair[1];
        // 加密
        var encryptCode = ConfigTools.encrypt(privateKey, userName);
        var decryptCode = ConfigTools.decrypt(publicKey, encryptCode);
        System.out.println(StrUtil.format("私钥:{}, 加密后:{}", privateKey, encryptCode));
        System.out.println(StrUtil.format("公钥:{}, 解密后:{}", publicKey, decryptCode));
    }

}
