package com.cloud.mall.app.controller;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.StrUtil;
import com.cloud.mall.domain.workbench.result.exp.BizException;
import com.cloud.mall.domain.workbench.result.exp.BizExceptionProperties;
import com.cloud.mall.domain.workbench.user.model.UserDomainService;
import com.cloud.mall.infrastructure.tools.function.SimpleFunction;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 夜痕
 * @Date: 2022-02-03 3:13 下午
 * @Description:
 */
@Api(tags = "认证控制器")
@RequestMapping("/login")
@RestController
public class LoginAndRegistryController {
    @Autowired
    private UserDomainService userDomainService;
    @Autowired
    private SimpleFunction simpleFunction;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("用户认证")
    @GetMapping("/authentication")
    public Long validateUserLogin(@RequestParam("account") final String account,
        @RequestParam("pswd") final String pswd) {
        if (!this.simpleFunction.validateParamNotBlank().apply(Lists.newArrayList(account, pswd))) {
            throw new BizException(BizExceptionProperties.PARAM_VALIDATE_NOT_PASS.getMsg());
        }

        return this.userDomainService.authenticationUserLogin(account, pswd);
    }

    @ApiOperation("用户注册")
    @GetMapping("/registry")
    public Long registerUserAndAuthentication(@RequestParam("account") final String account,
        @RequestParam("pswd") final String pswd,
        @RequestParam("graphValidateCode") final String graphValidateCode) {
        if (!this.simpleFunction.validateParamNotBlank().apply(Lists.newArrayList(account, pswd, graphValidateCode))) {
            throw new BizException(BizExceptionProperties.PARAM_VALIDATE_NOT_PASS.getMsg());
        }

        final String key = Joiner.on("-")
            .skipNulls()
            .join(Lists.newArrayList(account, "registry", "captcha"));
        final String captcha = (String)this.redisTemplate.opsForValue().get(key);
        if (StrUtil.isBlank(captcha) || !captcha.equals(graphValidateCode)) {
            throw new BizException(BizExceptionProperties.CAPTCHA_VALIDATE_NOT_PASS.getMsg());
        }
        return this.userDomainService.userAuthentication(account, pswd);
    }

    @ApiOperation("获取图形验证码")
    @PostMapping("/getCaptcha")
    public void getCaptcha(final HttpServletRequest request, final HttpServletResponse response,
        @RequestParam("account") final String account) throws IOException {
        CaptchaUtil.out(request, response);
        final String captcha = (String)request.getSession().getAttribute("captcha");
        // 清除 session中验证码
        CaptchaUtil.clear(request);
        System.out.println("输出的图形验证码:" + captcha);
        // 存储到 redis中去 expire key:${account-registry-captcha}
        final String key = Joiner.on("-")
            .skipNulls()
            .join(Lists.newArrayList(account, "registry", "captcha"));
        this.redisTemplate.opsForValue().set(key, captcha, 5L, TimeUnit.MINUTES);
    }
}
