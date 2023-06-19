package org.sacid.spring_mvc_config.service;

import org.springframework.web.util.ContentCachingRequestWrapper;

public interface HistoryService {

  void insertHistory(ContentCachingRequestWrapper request);
}
