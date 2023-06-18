package org.sacid.spring_mvc_config.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Slf4j
@Service
public class HistoryServiceImpl implements HistoryService{

  @Override
  public void insertHistory(ContentCachingRequestWrapper request) {
    log.info("request: {}", request);
  }
}
