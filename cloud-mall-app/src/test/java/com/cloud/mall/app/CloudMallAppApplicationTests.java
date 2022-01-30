package com.cloud.mall.app;

import com.cloud.mall.infrastructure.dataObject.workbench.user.UserDO;
import com.cloud.mall.infrastructure.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = CloudMallAppApplication.class)
class CloudMallAppApplicationTests {

    @Autowired
    private UserMapper userMapper;

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

}
