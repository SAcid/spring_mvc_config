package org.sacid.spring_mvc_config.config;

import org.sacid.spring_mvc_config.annotation.CustomValue;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

/**
 *  Contoller의 ResponseBody annotation이 선언된 argument의 field에 CustomValue annotation이 있는 경우 값을 변경한다.
 */
@Component
public class ResponseBodyCustomValueHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {
  private final HandlerMethodReturnValueHandler delegate;

  public ResponseBodyCustomValueHandlerMethodReturnValueHandler(
      RequestMappingHandlerAdapter requestMappingHandlerAdapter) {

    this.delegate = requestMappingHandlerAdapter.getReturnValueHandlers().stream()
        .filter(RequestResponseBodyMethodProcessor.class::isInstance)
        .findFirst().orElseThrow(() -> new IllegalArgumentException("RequestResponseBodyMethodProcessor ReturnValueHandlers is not found"));
    config(requestMappingHandlerAdapter);
  }

  /*
     WebMvcConfigurer의 addReturnValueHandlers를 통해 설정할 경우 기본으로 ResponseBody를 처리하는 RequestResponseBodyMethodProcessor 보다 우선순위가 낮아 실행되지 않는다.
     - RequestMappingHandlerAdapter의 getDefaultReturnValueHandlers 메소드 참고
     RequestMappingHandlerAdapter에 직접 접근하여 설정한다.
   */
  private void config(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
    List<HandlerMethodReturnValueHandler> returnValueHandlers = Objects.requireNonNull(requestMappingHandlerAdapter.getReturnValueHandlers());
    List<HandlerMethodReturnValueHandler> customReturnValueHandlers = new ArrayList<>(returnValueHandlers.size() + 1);
    returnValueHandlers.stream().filter(RequestResponseBodyMethodProcessor.class::isInstance).
        findFirst().ifPresent(resolver -> {
          customReturnValueHandlers.addAll(returnValueHandlers);
          customReturnValueHandlers.add(Math.max(returnValueHandlers.indexOf(resolver), 0), this);
        });
    requestMappingHandlerAdapter.setReturnValueHandlers(customReturnValueHandlers);
  }

  @Override
  public boolean supportsReturnType(MethodParameter returnType) {
    Method method = returnType.getMethod();
    if (Objects.isNull(method)) {
      return false;
    }
    return
        Objects.nonNull(AnnotationUtils.findAnnotation(method.getReturnType(), CustomValue.class))
        && delegate.supportsReturnType(returnType);
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
        ReflectionUtils.setField(field, returnValue, "http://localhost/" + ReflectionUtils.getField(field, returnValue));
      }
    });

    delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
  }
}
