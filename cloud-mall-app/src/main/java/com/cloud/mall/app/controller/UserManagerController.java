package com.cloud.mall.app.controller;

import java.io.IOException;
import java.util.Objects;

import com.cloud.mall.app.aop.annotaion.PortalSessionAnnotation;
import com.cloud.mall.domain.workbench.statistics.StatisticsDomainService;
import com.cloud.mall.domain.workbench.user.UserDomainService;
import com.cloud.mall.domain.workbench.user.model.UserHitsVo;
import com.cloud.mall.domain.workbench.user.model.UserInfoVO;
import com.cloud.mall.infrastructure.dataObject.workbench.user.UserDO;
import com.cloud.mall.infrastructure.dataObject.workbench.user.UserIdentityEnum;
import com.cloud.mall.infrastructure.result.ResultDto;
import com.cloud.mall.infrastructure.result.exp.BizException;
import com.cloud.mall.infrastructure.result.exp.BizExceptionProperties;
import com.cloud.mall.infrastructure.tools.function.SimpleFunction;
import com.cloud.mall.infrastructure.utils.SessionUtil;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: 夜痕
 * @Date: 2022-02-05 12:34 上午
 * @Description: 用户相关控制器
 */
@Api(tags = "用户管理控制器")
@RequestMapping("/user")
@RestController
public class UserManagerController {
    @Autowired
    private UserDomainService userDomainService;
    @Autowired
    private SimpleFunction simpleFunction;
    @Autowired
    private StatisticsDomainService statisticsDomainService;

    @ApiOperation("用户上传头像")
    @PortalSessionAnnotation
    @PostMapping("/headImgUpload")
    public ResultDto<String> uploadHeadImg(
        @RequestParam(value = "file", defaultValue = "null") final MultipartFile file) {
        try {
            final Long userId = SessionUtil.currentSession().getUserId();
            System.out.println("userId:" + userId);
            return new ResultDto<>(this.userDomainService.uploadHeadImg(file, userId));
        } catch (final IOException e) {
            throw new BizException(BizExceptionProperties.FILE_UPLOAD_FAILED.getMsg());
        }
    }

    @ApiOperation("用户绑定手机号码")
    @GetMapping("/userBindMobile")
    public ResultDto<Boolean> bindMobileToUser(@RequestParam("account") final String account,
        @RequestParam("mobile") final String mobile) {
        if (!this.simpleFunction.validateParamNotBlank().apply(Lists.newArrayList(account, mobile))) {
            throw new BizException(BizExceptionProperties.PARAM_VALIDATE_NOT_PASS.getMsg());
        }

        return new ResultDto<>(this.userDomainService.userBindMobile(account, mobile));
    }

    @ApiOperation("自主更新用户信息")
    @PostMapping("/updateUserInfo")
    @PortalSessionAnnotation
    public ResultDto<Void> updateUserInfo(@RequestBody final UserDO userDO) {
        if (Objects.isNull(userDO)) {
            throw new BizException(BizExceptionProperties.PARAM_VALIDATE_NOT_PASS.getMsg());
        }

        this.userDomainService.updateUserInfo(userDO);
        return new ResultDto<>();
    }

    @ApiOperation("获取用户点击比例图")
    @GetMapping("/getUserRecentlyHits")
    @PortalSessionAnnotation
    public ResultDto<UserHitsVo> getUserRecentlyHits() {
        final Long userId = SessionUtil.currentSession().getUserId();
        return new ResultDto<>(this.userDomainService.getUserRecentlyHits(userId));
    }

    @ApiOperation("返回用户标识")
    @GetMapping("/getUserIdentity")
    @PortalSessionAnnotation
    public ResultDto<String> getUserIdentity() {
        final UserIdentityEnum userIdentityEnum = SessionUtil.currentSession().getUserIdentityEnum();
        return new ResultDto<>(userIdentityEnum.getDesc());
    }

    @ApiOperation(("/获取用户信息"))
    @GetMapping("/getUserInfo")
    public ResultDto<UserInfoVO> getUserInfo(final String userNick) {
        return new ResultDto<>(userDomainService.getUserInfo(userNick));
    }

}
