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
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

/**
 *  Contoller의 RequestBody annotation이 선언된 argument의 field에 CustomValue annotation이 있는 경우 값을 변경한다.
 */
@Component
public class RequestBodyCustomValueHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver{
  private final HandlerMethodArgumentResolver delegate;

  public RequestBodyCustomValueHandlerMethodArgumentResolver(
      RequestMappingHandlerAdapter requestMappingHandlerAdapter
  ) {
    this.delegate = requestMappingHandlerAdapter.getArgumentResolvers().stream()
        .filter(RequestResponseBodyMethodProcessor.class::isInstance)
        .findFirst().orElseThrow(() -> new IllegalArgumentException("RequestResponseBodyMethodProcessor ReturnValueHandlers is not found"));
    config(requestMappingHandlerAdapter);
  }

  private void config(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
    /*
       WebMvcConfigurer의 addArgumentResolvers를 통해 설정할 경우 기본으로 RequestBody를 처리하는 RequestResponseBodyMethodProcessor보다 우선순위가 낮아 실행되지 않는다.
       - RequestMappingHandlerAdapter의 getDefaultArgumentResolvers 메소드 참고
       RequestMappingHandlerAdapter에 직접 접근하여 설정한다.
     */
    List<HandlerMethodArgumentResolver> argumentResolvers = Objects.requireNonNull(
        requestMappingHandlerAdapter.getArgumentResolvers());
    List<HandlerMethodArgumentResolver> customResolvers = new ArrayList<>(
        argumentResolvers.size() + 1);
    argumentResolvers.stream()
        .filter(RequestResponseBodyMethodProcessor.class::isInstance).
        findFirst().ifPresent(resolver -> {
          customResolvers.addAll(argumentResolvers);
          customResolvers.add(Math.max(argumentResolvers.indexOf(resolver), 0), this);
        });
    requestMappingHandlerAdapter.setArgumentResolvers(customResolvers);
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {

    Method method = parameter.getMethod();
    if (Objects.isNull(method)) {
      return false;
    }
    return
        Objects.nonNull(AnnotationUtils.findAnnotation(method.getReturnType(), CustomValue.class))
            && delegate.supportsParameter(parameter)
        ;
  }

  @Override
  public Object resolveArgument(MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory) throws Exception {

    Object arg = delegate.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
    if (Objects.isNull(arg)) {
      return null;
    }

    Class<?> clazz = arg.getClass();
    ReflectionUtils.doWithFields(clazz, field -> {
      if (Objects.nonNull(AnnotationUtils.findAnnotation(field, CustomValue.class))) {
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, arg, "change");
      }
    });

    return arg;
  }
}
