package com.cloud.mall.app.controller;

import java.util.List;

import com.cloud.mall.app.aop.annotaion.PortalSessionAnnotation;
import com.cloud.mall.domain.workbench.tags.model.TagEnum;
import com.cloud.mall.domain.workbench.tags.model.TagsDomainService;
import com.cloud.mall.infrastructure.data.dao.tags.TagsWrapper;
import com.cloud.mall.infrastructure.dataObject.workbench.tags.TagsDO;
import com.cloud.mall.infrastructure.result.ResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 远道
 * @Date: 2022-04-11 9:38 下午
 * @Description:
 */
@Api(tags = "标签管理器")
@RequestMapping("/tags")
@RestController
public class TagsController {

    @Autowired
    private TagsDomainService tagsDomainService;
    @Autowired
    private TagsWrapper tagsWrapper;

    @ApiOperation("/展示所有标签")
    @GetMapping("/getAllTags")
    public ResultDto<List<TagsDO>> getAllTags() {
        return new ResultDto<>(this.tagsDomainService.getAllTags());
    }

    @ApiOperation("/展示所有的标签类别")
    @GetMapping("/getAllTagsType")
    public ResultDto<List<String>> getAllTagsType() {
        return new ResultDto<>(this.tagsDomainService.getAllTagsType());
    }

    @ApiOperation("/商家新增标签")
    @PostMapping("/addNewTag")
    @PortalSessionAnnotation(expectedUserAdmin = true)
    public ResultDto<Void> addNewTag(@RequestBody final TagsDO tagsDO) {
        this.tagsWrapper.addNewTag(tagsDO);
        return new ResultDto<>();
    }
}
