package com.ctrip.framework.apollo.portal.spi.github;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ctrip.framework.apollo.portal.component.config.PortalConfig;
import com.ctrip.framework.apollo.portal.entity.bo.UserInfo;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPSearchResults;

/**
 * Ldap服务
 * 
 * @author Ternence
 * @date 2017年6月15日
 */
@Service
public class LdapService {

  @Autowired
  private PortalConfig portalConfig;

  private Logger logger = LoggerFactory.getLogger(LdapService.class);

  /**
   * 最多返回的用户数量
   */
  private static final int USER_MAX_COUNT = 10;

  public List<UserInfo> searchUsers(String keyword) {

    String filter = "(&(cn=*" + keyword + "*)(objectClass=person))";//
    return listUser(filter);

  }

  public UserInfo findByUserId(String userId) {

    String filter = "(&(cn=" + userId + ")(objectClass=person))";
    List<UserInfo> list = listUser(filter);
    return CollectionUtils.isEmpty(list) ? null : list.get(0);

  }

  public List<UserInfo> findByUserIds(List<String> userIds) {
    List<UserInfo> result = new ArrayList<>();
    if (userIds == null) {
      return result;
    }

    for (String userid : userIds) {
      String filter = "(&(cn=" + userid + ")(objectClass=person))";//
      result.addAll(listUser(filter));
    }

    return result;
  }

  private List<UserInfo> listUser(String filter) {
    List<UserInfo> result = new ArrayList<>();

    String ldapHost = portalConfig.ldapHost();
    String loginDN = portalConfig.ldapDn();
    String password = portalConfig.ldapPassword();
    String searchBase = portalConfig.ldapSearchbase();

    int ldapPort = LDAPConnection.DEFAULT_PORT;
    // 查询范围
    // SCOPE_BASE、SCOPE_ONE、SCOPE_SUB、SCOPE_SUBORDINATESUBTREE
    int searchScope = LDAPConnection.SCOPE_SUB;

    LDAPConnection lc = new LDAPConnection();
    try {
      lc.connect(ldapHost, ldapPort);
      lc.bind(LDAPConnection.LDAP_V3, loginDN, password.getBytes("UTF8"));
      LDAPSearchResults searchResults = lc.search(searchBase, searchScope, filter, null, false);

      while (searchResults.hasMore()) {
        LDAPEntry nextEntry = null;
        try {
          nextEntry = searchResults.next();
        } catch (LDAPException e) {
          logger.error("Ldap error: ", e);
          if (e.getResultCode() == LDAPException.LDAP_TIMEOUT
              || e.getResultCode() == LDAPException.CONNECT_ERROR) {
            break;
          } else {
            continue;
          }
        }

        LDAPAttributeSet attributeSet = nextEntry.getAttributeSet();
        UserInfo user = new UserInfo();
        user.setUserId(attributeSet.getAttribute("cn").getStringValue());
        user.setName(attributeSet.getAttribute("displayName").getStringValue());
        user.setEmail(attributeSet.getAttribute("mail").getStringValue());
        result.add(user);
        if (result.size() >= USER_MAX_COUNT) {
          break;
        }
      }


    } catch (LDAPException | UnsupportedEncodingException e) {
      logger.error("Ldap error: ", e);
    } finally {
      try {
        if (lc.isConnected()) {
          lc.disconnect();
        }
      } catch (Exception e) {
        logger.error("Ldap disconnect error: ", e);
      }
    }
    return result;
  }
}
