package com.ctrip.framework.apollo.portal.spi.defaultimpl;

import com.ctrip.framework.apollo.portal.spi.LogoutHandler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class DefaultLogoutHandler implements LogoutHandler {
	
	private static Logger logger = LoggerFactory.getLogger(DefaultLogoutHandler.class);
	
  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response) {
    try {
    	//将session销毁
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
			logger.info("session销毁成功");
		}
	
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(null);
	
		SecurityContextHolder.clearContext();
        
		logger.info("security清除成功");
		
		response.sendRedirect("/");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
