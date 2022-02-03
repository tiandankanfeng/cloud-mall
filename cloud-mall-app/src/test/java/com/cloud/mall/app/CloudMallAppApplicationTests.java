package com.cloud.mall.app;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.cloud.mall.infrastructure.dataObject.workbench.user.UserDO;
import com.cloud.mall.infrastructure.mapper.UserMapper;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;

@SpringBootTest(classes = CloudMallAppApplication.class)
class CloudMallAppApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringEncryptor encryptor;

    @Test
    void contextLoads() {
    }

    @Test
    public void testInsertIntoUser() {
        //final UserDO userDO = UserDO.builder()
        //    .id(1L)
        //    .createNick("yuandao")
        //    .modifiedNick("yuandao")
        //    .account("yuandao")
        //    .pswd("*****")
        //    .age(21)
        //    .email("2252578955@qq.com")
        //    .mobile("19855143393")
        //    .headImg("https://XXXUrl")
        //    .userIdentity(UserIdentityEnum.ORDINARY_USER)
        //    .sex(SexEnum.MAN)
        //    .tags("1,2")
        //    .build();
        //this.userMapper.insert(userDO);
    }

    @Test
    public void testUserOnSelect() {
        final UserDO userDO = this.userMapper.selectById(1L);
        System.out.println(userDO.toString());
    }

    @Test
    public void testRedis() {
        this.redisTemplate.opsForValue().set("name", "yuandao2");
        System.out.println(this.redisTemplate.opsForValue().get("name"));
    }

    @Test
    public void testEncrypt() {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        System.out.println(new String(DigestUtil.md5("wu913428!")).equals(
            new String(md5.digest("wu913428!".getBytes(StandardCharsets.UTF_8)))));

        System.out.println(new String(DigestUtil.md5("yehen", CharsetUtil.UTF_8)).equals(
            new String(DigestUtil.md5("yehen", CharsetUtil.UTF_8))));
    }

    //@Test
    //public void testJasypt() {
    //    System.out.println(this.encryptor.encrypt("yuandao"));
    //    System.out.println(this.encryptor.encrypt("sky.liangye-xo.xyz"));
    //    System.out.println(this.encryptor.encrypt("6379"));
    //    System.out.println(this.encryptor.encrypt("Wu913428!"));
    //}

}
