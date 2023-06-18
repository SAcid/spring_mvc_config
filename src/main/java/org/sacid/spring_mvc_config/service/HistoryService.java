package org.sacid.spring_mvc_config.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.util.ContentCachingRequestWrapper;

public interface HistoryService {

  void insertHistory(ContentCachingRequestWrapper request);
}
