package com.cloud.mall.implementation.tags;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.cloud.mall.domain.workbench.tags.model.TagEnum;
import com.cloud.mall.domain.workbench.tags.model.TagsDomainService;
import com.cloud.mall.infrastructure.data.dao.tags.TagsWrapper;
import com.cloud.mall.infrastructure.dataObject.workbench.tags.TagsDO;
import com.cloud.mall.infrastructure.mapper.TagsMapper;
import com.cloud.mall.infrastructure.session.PortalSession;
import com.cloud.mall.infrastructure.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 远道
 * @Date: 2022-04-11 9:06 下午
 * @Description:
 */
@Service
public class TagsDomainServiceImpl implements TagsDomainService {

    @Autowired
    private TagsWrapper tagsWrapper;

    @Override
    public List<String> getAllTagsType() {
        return Arrays.stream(TagEnum.values())
            .map(TagEnum::getDesc)
            .collect(Collectors.toList());
    }

    @Override
    public List<TagsDO> getAllTags() {
        return this.tagsWrapper.getAllTags();
    }

}
