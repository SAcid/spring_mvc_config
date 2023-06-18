package org.sacid.spring_mvc_config.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.sacid.spring_mvc_config.model.CustomRequest;
import org.sacid.spring_mvc_config.model.HeaderKey;
import org.sacid.spring_mvc_config.service.HistoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
@Import({HistoryServiceImpl.class})
@WebMvcTest(AuthController.class)
class HeaderKeyControllerTest {

  @Autowired
  MockMvc mockMvc;

  @SpyBean
  HeaderKeyController headerKeyController;

  @Captor
  ArgumentCaptor<HeaderKey> headerKeyArgumentCaptor;

  @Test
  public void header_key_exists() throws Exception {
    mockMvc.perform(get("/header-key")
            .header("header-key", "test")
        )
        .andExpect(status().isOk())
        .andExpect(content().string("test"));

    verify(headerKeyController, times(1)).getHeaderKey(headerKeyArgumentCaptor.capture());
    ReflectionUtils.findConstructors(HeaderKey.class, constructor -> Modifier.isPrivate(constructor.getModifiers()))
        .stream()
        .findFirst()
        .ifPresentOrElse(
            constructor -> {
              constructor.setAccessible(true);
              try {
                assertThat(headerKeyArgumentCaptor.getValue()).isEqualTo(constructor.newInstance("test"));
              } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException("can not invoke CustomRequest constructor");
              }
            },
            () -> fail("CustomRequest constructor is not found")
        );
  }


  @Test
  public void header_key_not_exists() throws Exception {
    mockMvc.perform(get("/header-key")
        )
        .andExpect(status().isOk())
        .andExpect(content().string(""));

    verify(headerKeyController, times(1)).getHeaderKey(headerKeyArgumentCaptor.capture());
    ReflectionUtils.findConstructors(HeaderKey.class, constructor -> Modifier.isPrivate(constructor.getModifiers()))
        .stream()
        .findFirst()
        .ifPresentOrElse(
            constructor -> {
              constructor.setAccessible(true);
              try {
                assertThat(headerKeyArgumentCaptor.getValue()).isEqualTo(constructor.newInstance(""));
              } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException("can not invoke CustomRequest constructor");
              }
            },
            () -> fail("CustomRequest constructor is not found")
        );
  }
}