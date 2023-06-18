package org.sacid.spring_mvc_config.config;

import javax.servlet.http.HttpServletRequest;
import org.sacid.spring_mvc_config.model.HeaderKey;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * controller method arguments 중 HeaderKey 있는 경우 생성
 */
public class HeaderKeyHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver{
  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return HeaderKey.class.isAssignableFrom(parameter.getParameterType());
  }

  @Override
  public Object resolveArgument(MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory) throws Exception {

    HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
    if (request == null) {
      throw new IllegalStateException("Current request is not of type HttpServletRequest: " + webRequest);
    }

    return HeaderKey.of(request.getHeader("header-key"));
  }
}
