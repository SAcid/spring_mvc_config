package org.sacid.spring_mvc_config.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

@Import({ResponseBodyCustomValueHandlerMethodReturnValueHandler.class})
@WebMvcTest
class RequestMappingHandlerAdaptersTest {
  @Autowired
  ApplicationContext applicationContext;

  @Test
  void argumentResolvers_order() {
    RequestMappingHandlerAdapter bean = applicationContext.getBean(
        RequestMappingHandlerAdapter.class);

    List<HandlerMethodArgumentResolver> argumentResolvers = bean.getArgumentResolvers();

    assertThat(argumentResolvers).isNotNull();
    assertOrder(argumentResolvers, RequestBodyCustomValueHandlerMethodArgumentResolver.class, 7);
    assertOrder(argumentResolvers, RequestResponseBodyMethodProcessor.class, 8);
    assertOrder(argumentResolvers, HeaderKeyHandlerMethodArgumentResolver.class, 25);
  }

  @Test
  void returnValueHandlers_order() {
    RequestMappingHandlerAdapter bean = applicationContext.getBean(
        RequestMappingHandlerAdapter.class);

    List<HandlerMethodReturnValueHandler> returnValueHandlers = bean.getReturnValueHandlers();

    assertThat(returnValueHandlers).isNotNull();
    assertOrder(returnValueHandlers, ResponseBodyCustomValueHandlerMethodReturnValueHandler.class, 11);
    assertOrder(returnValueHandlers, RequestResponseBodyMethodProcessor.class, 12);
    assertOrder(returnValueHandlers, CustomValueHandlerMethodReturnValueHandler.class, 15);
  }

  private <T, S> void assertOrder(List<T> list, Class<S> clazz, int idx ) {
    list.stream()
        .filter(clazz::isInstance).
        findFirst()
        .ifPresentOrElse(
            v -> assertThat(list.indexOf(v)).isEqualTo(idx),
            () -> fail(clazz.getName() + ".class is not found")
        );
  }
}
