package org.sacid.spring_mvc_config.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.sacid.spring_mvc_config.config.RequestBodyCustomValueHandlerMethodArgumentResolver;
import org.sacid.spring_mvc_config.config.ResponseBodyCustomValueHandlerMethodReturnValueHandler;
import org.sacid.spring_mvc_config.controller.CustomRestController;
import org.sacid.spring_mvc_config.model.CustomRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@Import({RequestBodyCustomValueHandlerMethodArgumentResolver.class, ResponseBodyCustomValueHandlerMethodReturnValueHandler.class})
@WebMvcTest(CustomRestController.class)
class CustomRestControllerTest {
  @Autowired
  MockMvc mockMvc;

  @SpyBean
  CustomRestController customRestController;

  @Captor
  ArgumentCaptor<String> idCaptor;

  @Captor
  ArgumentCaptor<CustomRequest> customRequestCaptor;


  @Test
  void test() throws Exception {
    mockMvc.perform(post("/custom/222")
            .content("{\"title\" : \"title\", \"content\" : \"content\"}")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string("{\"url\":\"http://localhost/222\",\"title\":\"title\",\"content\":\"content\"}"));

    verify(customRestController).custom(idCaptor.capture(), customRequestCaptor.capture());

    assertThat(idCaptor.getValue()).isEqualTo("222");
    ReflectionUtils.findConstructors(CustomRequest.class, constructor -> Modifier.isPrivate(constructor.getModifiers()))
        .stream()
        .findFirst()
        .ifPresentOrElse(
            constructor -> {
              constructor.setAccessible(true);
              try {
                assertThat(customRequestCaptor.getValue()).isEqualTo(constructor.newInstance("change", "title", "content"));
              } catch (InstantiationException| IllegalAccessException |  InvocationTargetException e) {
                throw new IllegalStateException("can not invoke CustomRequest constructor");
              }
            },
            () -> fail("CustomRequest constructor is not found")
        );
  }
}