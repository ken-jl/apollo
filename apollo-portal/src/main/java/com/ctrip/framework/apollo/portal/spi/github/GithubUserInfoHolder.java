package com.ctrip.framework.apollo.portal.spi.github;

import org.jasig.cas.client.util.AssertionHolder;
import org.springframework.stereotype.Service;

import com.ctrip.framework.apollo.portal.entity.bo.UserInfo;
import com.ctrip.framework.apollo.portal.spi.UserInfoHolder;

/**
 * @author Ternence
 * @date 2017年6月15日
 */
@Service
public class GithubUserInfoHolder implements UserInfoHolder {

  @Override
  public UserInfo getUser() {
    UserInfo userInfo = new UserInfo();
    userInfo.setUserId(AssertionHolder.getAssertion().getPrincipal().getName());
    return userInfo;
  }
}
