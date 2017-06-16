package com.ctrip.framework.apollo.portal.spi.defaultimpl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ctrip.framework.apollo.portal.spi.LogoutHandler;

public class DefaultLogoutHandler implements LogoutHandler {

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response) {
    try {
      request.getSession().invalidate(); // 过期session
      response.sendRedirect("/");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
