package org.sacid.spring_mvc_config.config;

import org.sacid.spring_mvc_config.annotation.CustomValue;
import java.lang.reflect.Method;
import java.util.Objects;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

public class CustomValueHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

  @Override
  public boolean supportsReturnType(MethodParameter returnType) {
    Method method = returnType.getMethod();
    if (Objects.isNull(method)) {
      return false;
    }
    return
        Objects.nonNull(AnnotationUtils.findAnnotation(method.getReturnType(), CustomValue.class));
  }

  @Override
  public void handleReturnValue(Object returnValue,
      MethodParameter returnType,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest) throws Exception {
    if (Objects.isNull(returnValue)) {
      return;
    }

    Class<?> clazz = returnValue.getClass();
    ReflectionUtils.doWithFields(clazz, field -> {
      if (Objects.nonNull(AnnotationUtils.findAnnotation(field, CustomValue.class))) {
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, returnValue, "http://localhost" + ReflectionUtils.getField(field, returnValue));
      }
    });

    // TODO implement
  }
}
