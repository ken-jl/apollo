package com.ctrip.framework.apollo.portal.spi.github;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctrip.framework.apollo.portal.entity.bo.UserInfo;
import com.ctrip.framework.apollo.portal.spi.UserService;

/**
 * 自定义用户服务
 * 
 * @author Ternence
 * @date 2017年6月15日
 */
@Service
public class GithubUserService implements UserService {

  @Autowired
  private LdapService ldapService;

  @Override
  public List<UserInfo> searchUsers(String keyword, int offset, int limit) {
    return ldapService.searchUsers(keyword);
  }

  @Override
  public UserInfo findByUserId(String userId) {
    return ldapService.findByUserId(userId);
  }

  @Override
  public List<UserInfo> findByUserIds(List<String> userIds) {

    return ldapService.findByUserIds(userIds);
  }
}
