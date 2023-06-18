package org.sacid.spring_mvc_config.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sacid.spring_mvc_config.handler.CustomValueHandlerMethodReturnValueHandler;
import org.sacid.spring_mvc_config.interceptor.AuthenticationInterceptor;
import org.sacid.spring_mvc_config.interceptor.CompletedInterceptor;
import org.sacid.spring_mvc_config.interceptor.LoggingHandlerInterceptor;
import org.sacid.spring_mvc_config.resolver.HeaderKeyHandlerMethodArgumentResolver;
import org.sacid.spring_mvc_config.service.HistoryService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
  private final HistoryService historyService;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LoggingHandlerInterceptor());
    registry.addInterceptor(new AuthenticationInterceptor())
        .addPathPatterns("/auth/**");
    registry.addInterceptor(new CompletedInterceptor(historyService));
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(new HeaderKeyHandlerMethodArgumentResolver());
  }

  @Override
  public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
    handlers.add(new CustomValueHandlerMethodReturnValueHandler());
  }
}
