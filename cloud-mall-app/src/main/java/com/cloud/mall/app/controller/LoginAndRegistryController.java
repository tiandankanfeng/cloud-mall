package com.cloud.mall.app.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.cloud.mall.domain.workbench.user.UserDomainService;
import com.cloud.mall.infrastructure.data.dao.user.UserWrapper;
import com.cloud.mall.infrastructure.dataObject.workbench.user.UserDO;
import com.cloud.mall.infrastructure.result.ResultDto;
import com.cloud.mall.infrastructure.result.exp.BizException;
import com.cloud.mall.infrastructure.result.exp.BizExceptionProperties;
import com.cloud.mall.infrastructure.tools.function.SimpleFunction;
import com.cloud.mall.infrastructure.utils.RedisManager;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
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
    private RedisManager redisManager;
    @Autowired
    private UserWrapper userWrapper;

    @ApiOperation("用户密码认证")
    @GetMapping("/authentication")
    public ResultDto<Long> validateUserLogin(@RequestParam("account") final String account,
        @RequestParam("pswd") final String pswd,
        @ApiParam("图形验证码") @RequestParam("graphValidateCode") final String graphValidateCode) {
        if (!this.simpleFunction.validateParamNotBlank().apply(Lists.newArrayList(account, pswd, graphValidateCode))) {
            throw new BizException(BizExceptionProperties.PARAM_VALIDATE_NOT_PASS.getMsg());
        }

        final String key = Joiner.on("-")
            .skipNulls()
            .join(Lists.newArrayList(account, "registry", "captcha"));
        final String captcha = String.valueOf(this.redisManager.get(key));
        if (StrUtil.isBlank(captcha) || !captcha.equals(graphValidateCode)) {
            throw new BizException(BizExceptionProperties.CAPTCHA_VALIDATE_NOT_PASS.getMsg());
        }

        return new ResultDto<>(this.userDomainService.authenticationUserLogin(account, pswd));
    }

    @ApiOperation("用户短信验证")
    @GetMapping("/validateUserLoginByMobile")
    public ResultDto<Long> validateUserLoginByMobile(@RequestParam("mobile") final String mobile,
        @RequestParam("validateMsgCode") final String validateMsgCode) {
        if (!this.simpleFunction.validateParamNotBlank().apply(Lists.newArrayList(mobile, validateMsgCode))) {
            throw new BizException(BizExceptionProperties.PARAM_VALIDATE_NOT_PASS.getMsg());
        }

        // 获取账户信息
        final List<UserDO> userDOS = this.userWrapper.queryByUserParam(new UserDO().setMobile(mobile));
        if (CollectionUtils.isEmpty(userDOS)) {
            throw new BizException(BizExceptionProperties.MOBILE_DO_NOT_BIND_ANY_USER.getMsg());
        }

        final String account = userDOS.get(0).getAccount();
        // 验证
        if (!this.userDomainService.validateMobileMsgCode(account, validateMsgCode)) {
            throw new BizException(BizExceptionProperties.MOBILE_MSG_NOT_PASS.getMsg());
        }

        return new ResultDto<>(userDOS.get(0).getId());
    }

    @ApiOperation("用户注册")
    @GetMapping("/registry")
    public ResultDto<Long> registerUserAndAuthentication(@RequestParam("account") final String account,
        @RequestParam("pswd") final String pswd,
        @RequestParam("captcha") final String captcha) {
        if (!this.simpleFunction.validateParamNotBlank().apply(Lists.newArrayList(account, pswd, captcha))) {
            throw new BizException(BizExceptionProperties.PARAM_VALIDATE_NOT_PASS.getMsg());
        }
        // 验重账户
        this.validateUserAccountDistinct(account);

        final String msgKey = Joiner.on("-")
            .skipNulls()
            .join(Lists.newArrayList(account, "registry", "captcha"));
        final String captchaCacheCode = String.valueOf(this.redisManager.get(msgKey));

        if (StrUtil.isBlank(captchaCacheCode) || !captchaCacheCode.equals(captcha)) {
            throw new BizException(BizExceptionProperties.GRAPH_CAPTCHA_VALIDATE_NOT_PASS.getMsg());
        }

        return new ResultDto<>(this.userDomainService.userAuthentication(account, pswd));
    }

    private ResultDto<Void> validateUserAccountDistinct(final String account) {
        final List<UserDO> userDOList = this.userWrapper.queryByUserParam(new UserDO().setAccount(account));

        if (CollectionUtils.isNotEmpty(userDOList)) {
            throw new BizException(BizExceptionProperties.ACCOUNT_ALREADY_USERD_BY_OTHERS.getMsg());
        }

        return new ResultDto<>();
    }

    @ApiOperation("获取图形验证码")
    @PostMapping("/getCaptcha")
    public ResultDto<Void> getCaptcha(final HttpServletRequest request, final HttpServletResponse response,
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
        this.redisManager.set(key, captcha, 5L, TimeUnit.MINUTES);

        return new ResultDto<>();
    }
}
