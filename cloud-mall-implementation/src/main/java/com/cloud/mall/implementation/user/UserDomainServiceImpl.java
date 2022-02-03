package com.cloud.mall.implementation.user;

import java.util.List;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.cloud.mall.domain.workbench.result.exp.BizException;
import com.cloud.mall.domain.workbench.result.exp.BizExceptionProperties;
import com.cloud.mall.domain.workbench.user.model.UserDomainService;
import com.cloud.mall.infrastructure.data.dao.user.UserWrapper;
import com.cloud.mall.infrastructure.dataObject.workbench.user.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 夜痕
 * @Date: 2022-02-03 3:17 下午
 * @Description:
 */
@Service
public class UserDomainServiceImpl implements UserDomainService {
    @Autowired
    private UserWrapper userWrapper;

    @Override
    public Long authenticationUserLogin(final String account, final String pswd) {
        final List<UserDO> userDOList = this.userWrapper.queryByUserParam(new UserDO()
            .setAccount(account));

        if (CollectionUtil.isNotEmpty(userDOList)) {
            final UserDO userDO = userDOList.get(0);
            return userDO.getPswd().equals(DigestUtil.md5(pswd, CharsetUtil.UTF_8)) ?
                userDO.getId() : 0L;
        }
        return 0L;
    }

    @Override
    public Long userAuthentication(final String account, final String pswd) {
        // 验重
        final List<UserDO> userDOList = this.userWrapper.queryByUserParam(new UserDO().setAccount(account));
        if (CollectionUtil.isNotEmpty(userDOList)) {
            throw new BizException(BizExceptionProperties.REPEATABLE_ACCOUNT.getMsg());
        }

        final String encryptPswd = new String(DigestUtil.md5(pswd, CharsetUtil.UTF_8));
        final UserDO userDO = new UserDO()
            .setAccount(account)
            .setPswd(encryptPswd);
        this.doUserAuthentication(userDO);
        // 返回标识
        final List<UserDO> userDOS = this.userWrapper.queryByUserParam(new UserDO().setAccount(account));
        return userDOS.get(0).getId();
    }

    private void doUserAuthentication(final UserDO userDO) {
        userDO.setModifiedNick(userDO.getAccount());
        userDO.setCreateNick(userDO.getAccount());

        this.userWrapper.insertUserRecord(userDO);
    }
}
