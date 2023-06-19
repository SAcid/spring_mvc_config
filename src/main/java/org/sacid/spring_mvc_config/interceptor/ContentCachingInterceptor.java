package org.sacid.spring_mvc_config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.sacid.spring_mvc_config.service.HistoryService;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

/**
 * request payload logging
 */
public class ContentCachingInterceptor implements HandlerInterceptor {
  private final HistoryService historyService;
  public ContentCachingInterceptor(HistoryService historyService) {
    this.historyService = historyService;
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler,
      @Nullable Exception ex) {

    if (requestMatcher(request)) {
      historyService.insertHistory((ContentCachingRequestWrapper)request);
    }
  }

  private boolean requestMatcher(HttpServletRequest request) {
    return request instanceof ContentCachingRequestWrapper;
  }
}
