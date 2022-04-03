package com.cloud.mall.implementation.user;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.cloud.mall.domain.workbench.user.model.UserDomainService;
import com.cloud.mall.infrastructure.data.dao.goods.GoodsWrapper;
import com.cloud.mall.infrastructure.data.dao.msg.MsgRecordWrapper;
import com.cloud.mall.infrastructure.data.dao.statistics.StatisticsWrapper;
import com.cloud.mall.infrastructure.data.dao.user.UserWrapper;
import com.cloud.mall.domain.workbench.file.FileUploadService;
import com.cloud.mall.infrastructure.dataObject.workbench.msg.MsgCodeEnum;
import com.cloud.mall.infrastructure.dataObject.workbench.msg.MsgContentProperties;
import com.cloud.mall.infrastructure.dataObject.workbench.msg.MsgRecordDO;
import com.cloud.mall.infrastructure.dataObject.workbench.statistics.StatisticsDO;
import com.cloud.mall.infrastructure.dataObject.workbench.user.UserDO;
import com.cloud.mall.infrastructure.result.exp.BizException;
import com.cloud.mall.infrastructure.result.exp.BizExceptionProperties;
import com.cloud.mall.infrastructure.tools.function.SimpleFunction;
import com.cloud.mall.infrastructure.utils.MsgSendUtil;
import com.cloud.mall.infrastructure.utils.RedisManager;
import com.cloud.mall.infrastructure.utils.SessionUtil;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: 夜痕
 * @Date: 2022-02-03 3:17 下午
 * @Description:
 */
@Service
public class UserDomainServiceImpl implements UserDomainService {
    @Autowired
    private UserWrapper userWrapper;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private MsgRecordWrapper msgRecordWrapper;
    @Autowired
    private RedisManager redisManager;
    @Autowired
    private GoodsWrapper goodsWrapper;
    @Autowired
    private SimpleFunction simpleFunction;
    @Autowired
    private StatisticsWrapper statisticsWrapper;

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    @Override
    public Long authenticationUserLogin(final String account, final String pswd) {
        final List<UserDO> userDOList = this.userWrapper.queryByUserParam(new UserDO()
            .setAccount(account));

        if (CollectionUtil.isNotEmpty(userDOList)) {
            final UserDO userDO = userDOList.get(0);
            return userDO.getPswd().equals(this.simpleFunction.encryptCode().apply(pswd)) ?
                userDO.getId() : 0L;
        }
        return 0L;
    }

    @Override
    public Long userAuthentication(final String account, final String pswd, final String mobile) {
        // 验重
        final List<UserDO> userDOList = this.userWrapper.queryByUserParam(new UserDO().setAccount(account));
        if (CollectionUtil.isNotEmpty(userDOList)) {
            throw new BizException(BizExceptionProperties.REPEATABLE_ACCOUNT.getMsg());
        }

        final UserDO userDO = new UserDO()
            .setAccount(account)
            .setPswd(this.simpleFunction.encryptCode().apply(pswd))
            .setMobile(mobile);

        this.doUserAuthentication(userDO);
        // 返回标识
        final List<UserDO> userDOS = this.userWrapper.queryByUserParam(new UserDO().setAccount(account));
        return userDOS.get(0).getId();
    }

    @Override
    public String uploadHeadImg(final MultipartFile file, final Long userId) throws IOException {
        final String imgUrl = this.fileUploadService.uploadImg(file);
        // update user info.
        final UserDO userDO = new UserDO();
        userDO.setId(userId);
        userDO.setHeadImg(imgUrl);

        this.userWrapper.updateUserInfo(userDO);
        return imgUrl;
    }

    @Override
    public Boolean userBindMobile(final String account, final String mobile) {
        final int rdmNum = this.random.nextInt(1000, 10000);
        // 消息体
        final String content = StrUtil.format(MsgContentProperties.USER_BIND_MOBILE, mobile, rdmNum);
        final Future<MsgCodeEnum> future = MsgSendUtil.sendMsg(mobile, content);

        try {
            // 记录消息 此时并没有 userId
            final MsgRecordDO msgRecordDO = new MsgRecordDO()
                .setUserNick(account)
                .setMobile(mobile)
                .setContent(content)
                .setIsSuccess(future.get().equals(MsgCodeEnum.MSG_SND_SUCCESS));
            this.msgRecordWrapper.insertRecord(msgRecordDO);

            final String msgKey = Joiner.on("-")
                .skipNulls()
                .join(Lists.newArrayList(account, "registry", "msgCode"));
            this.redisManager.set(msgKey, rdmNum, 5L, TimeUnit.MINUTES);
            return true;
        } catch (final InterruptedException e) {
            e.printStackTrace();
        } catch (final ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean validateMobileMsgCode(final String account, final String msgCode) {
        final String msgKey = Joiner.on("-")
            .skipNulls()
            .join(Lists.newArrayList(account, "registry", "msgCode"));
        final String cacheCode = String.valueOf(this.redisManager.get(msgKey));
        return msgCode.equals(cacheCode);
    }

    @Override
    public void updateUserInfo(final UserDO userDO) {
        final String userNick = SessionUtil.currentSession().getUserNick();
        if (!userNick.equals(userDO.getAccount())) {
            throw new BizException(BizExceptionProperties.ACCOUNT_NOT_ALLOW_MODIFY.getMsg());
        }

        this.userWrapper.updateUserInfo(userDO);
    }

    @Override
    public List<String> updateUserTagsByStatistics(final Long userId) {
        final UserDO userDO = this.userWrapper.queryByUserId(userId);
        if (Objects.nonNull(userDO)) {
            // 获取用户 tag统计信息
            final List<StatisticsDO> statisticsDOList = this.statisticsWrapper.queryByParamAndOrderByHits(
                new StatisticsDO().setUserId(userId));
            final List<String> statisticsTags = statisticsDOList.stream()
                // 点击数至少大于 1
                .filter(statisticsDO -> statisticsDO.getHits() > 1L)
                .map(StatisticsDO::getTag)
                .collect(Collectors.toList());
            final String userLatestTags = Joiner.on(",")
                .join(statisticsTags);
            // 用户 tags按照优先级进行排序
            userDO.setTags(userLatestTags);
            this.userWrapper.updateUserInfo(userDO);

            return statisticsTags;
        }
        return Lists.newArrayList();
    }

    private void doUserAuthentication(final UserDO userDO) {
        userDO.setModifiedNick(userDO.getAccount());
        userDO.setCreateNick(userDO.getAccount());

        this.userWrapper.insertUserRecord(userDO);
    }

}
