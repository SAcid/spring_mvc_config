package org.sacid.spring_mvc_config.interceptor;

import java.time.Instant;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * logging
 */
@Slf4j
public class LoggingHandlerInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    long startTime = Instant.now().toEpochMilli();
    log.info("Request URL: {} - Start Time: {}", request.getRequestURL().toString(), Instant.now());
    request.setAttribute("startTime", startTime);

    return true;
  }


  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    long startTime = (Long) request.getAttribute("startTime");
    log.info("Request URL: {} - Time Taken: {}", request.getRequestURL().toString() , (Instant.now().toEpochMilli() - startTime));
  }
}
