package com.cloud.mall.app.controller;

import com.cloud.mall.domain.workbench.result.exp.BizException;
import com.cloud.mall.domain.workbench.result.exp.BizExceptionProperties;
import com.cloud.mall.domain.workbench.user.model.UserDomainService;
import com.cloud.mall.infrastructure.tools.function.SimpleFunction;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 夜痕
 * @Date: 2022-02-03 3:13 下午
 * @Description:
 */
@RequestMapping("/login")
@RestController
public class LoginAndRegistryController {
    @Autowired
    private UserDomainService userDomainService;
    @Autowired
    private SimpleFunction simpleFunction;

    @GetMapping("/authentication")
    public Long validateUserLogin(final String account, final String pswd) {
        if (!this.simpleFunction.validateParamNotBlank().apply(Lists.newArrayList(account, pswd))) {
            throw new BizException(BizExceptionProperties.PARAM_VALIDATE_NOT_PASS.getMsg());
        }

        return this.userDomainService.authenticationUserLogin(account, pswd);
    }

    @GetMapping("/regisiry")
    public Long registerUserAndAuthentication(final String account, final String pswd, final String graphValidateCode) {
        if (!this.simpleFunction.validateParamNotBlank().apply(Lists.newArrayList(account, pswd, graphValidateCode))) {
            throw new BizException(BizExceptionProperties.PARAM_VALIDATE_NOT_PASS.getMsg());
        }

        // todo, 验证图形二维码

        return this.userDomainService.userAuthentication(account, pswd);
    }
}
