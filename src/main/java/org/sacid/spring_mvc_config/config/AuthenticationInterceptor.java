package org.sacid.spring_mvc_config.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request,
      HttpServletResponse response, Object handler) throws Exception {
    if (checkAuth(request)) {
      return true;
    }

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    return false;
  }

  private boolean checkAuth(HttpServletRequest request) {
    return StringUtils.hasText(request.getHeader("authentication-id"));
  }
}
