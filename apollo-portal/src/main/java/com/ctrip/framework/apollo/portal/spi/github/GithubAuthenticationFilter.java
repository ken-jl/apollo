package com.ctrip.framework.apollo.portal.spi.github;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.CollectionUtils;

/**
 * 自定义认证过滤器
 * 
 * @author Ternence
 * @date 2017年6月16日
 */
public class GithubAuthenticationFilter extends AuthenticationFilter {

  private List<String> excludeUrls = new ArrayList<>();

  @Override
  protected void initInternal(FilterConfig filterConfig) throws ServletException {

    initExcludeUrl(filterConfig);
    super.initInternal(filterConfig);
  }

  /**
   * 初始化例外的URL
   * 
   * @param filterConfig
   * @date 2017年6月16日
   * @author Ternence
   */
  private void initExcludeUrl(FilterConfig filterConfig) {
    Enumeration<String> parameterNames = filterConfig.getInitParameterNames();
    while (parameterNames.hasMoreElements()) {
      String paramName = parameterNames.nextElement();
      String paramValue = filterConfig.getInitParameter(paramName);

      if ("exclude".equals(paramValue)) {
        excludeUrls.add(paramName);
      }

    }
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {


    if (isExcludeRequest(request)) {
      filterChain.doFilter(request, response);
    }

    super.doFilter(request, response, filterChain);
  }

  private boolean isExcludeRequest(ServletRequest servletRequest) {
    String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();
    if (CollectionUtils.isEmpty(excludeUrls)) {
      return false;
    }

    for (String excludeUrl : excludeUrls) {
      if (Pattern.matches(excludeUrl, requestURI)) {
        return true;
      }
    }

    return false;
  }

}
