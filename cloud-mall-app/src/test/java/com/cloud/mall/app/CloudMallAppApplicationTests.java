package com.cloud.mall.app;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cloud.mall.infrastructure.dataObject.workbench.user.UserDO;
import com.cloud.mall.infrastructure.mapper.UserMapper;
import com.cloud.mall.infrastructure.utils.RedisManager;
import com.google.common.base.Splitter;
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
    @Autowired
    private RedisManager redisManager;

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
        final String yehen = "yehen";
        final LambdaQueryWrapper<UserDO> wrapper = Wrappers.<UserDO>lambdaQuery()
            .eq(Boolean.TRUE, UserDO::getModifiedNick, yehen)
            .or()
            .isNotNull(Boolean.TRUE, UserDO::getModifiedNick);
        final List<UserDO> userDOS = this.userMapper.selectList(wrapper);
        userDOS.stream()
            .forEach(System.out::println);
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

    @Test
    public void testJasypt() {
        System.out.println(this.encryptor.encrypt("oss-cn-zhangjiakou.aliyuncs.com"));
    }

    @Test
    public void testRandom() {
        final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        System.out.println(threadLocalRandom.nextInt(1000, 10000));
        System.out.println(threadLocalRandom.nextInt(1000, 10000));

        Splitter.on(",")
            .split("yuandao")
            .forEach(System.out::println);
    }

    @Test
    public void testRedisTask() {
        this.redisManager.addTask("yuandao", Instant.now());
    }

}
