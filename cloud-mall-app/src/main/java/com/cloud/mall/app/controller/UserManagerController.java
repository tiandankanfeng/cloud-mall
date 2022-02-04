package com.cloud.mall.app.controller;

import java.io.IOException;

import com.cloud.mall.app.aop.annotaion.PortalSessionAnnotation;
import com.cloud.mall.domain.workbench.result.exp.BizException;
import com.cloud.mall.domain.workbench.result.exp.BizExceptionProperties;
import com.cloud.mall.domain.workbench.user.model.UserDomainService;
import com.cloud.mall.infrastructure.utils.SessionUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: 夜痕
 * @Date: 2022-02-05 12:34 上午
 * @Description: 用户相关控制器
 */
@RequestMapping("/user")
@RestController
public class UserManagerController {
    @Autowired
    private UserDomainService userDomainService;

    @ApiOperation("用户上传头像")
    @PortalSessionAnnotation
    @PostMapping("/headImgUpload")
    public String uploadHeadImg(@RequestParam(value = "file", defaultValue = "null") final MultipartFile file) {
        try {
            final Long userId = SessionUtil.currentSession().getUserId();
            System.out.println("userId:" + userId);
            return this.userDomainService.uploadHeadImg(file, userId);
        } catch (final IOException e) {
            throw new BizException(BizExceptionProperties.FILE_UPLOAD_FAILED.getMsg());
        }
    }
}
