package org.sacid.spring_mvc_config.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.sacid.spring_mvc_config.filter.ContentCachingRequestWrapperFilter;
import org.sacid.spring_mvc_config.handler.ResponseBodyCustomValueHandlerMethodReturnValueHandler;
import org.sacid.spring_mvc_config.model.CustomRequest;
import org.sacid.spring_mvc_config.resolver.RequestBodyCustomValueHandlerMethodArgumentResolver;
import org.sacid.spring_mvc_config.service.HistoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;
import org.springframework.web.util.ContentCachingRequestWrapper;


@TestConfiguration
class CustomMockMvcBuilderCustomizer implements MockMvcBuilderCustomizer {
  @Override
  public void customize(ConfigurableMockMvcBuilder<?> builder) {
    builder.addFilters(new ContentCachingRequestWrapperFilter());
  }
}
@Import({
    RequestBodyCustomValueHandlerMethodArgumentResolver.class,
    ResponseBodyCustomValueHandlerMethodReturnValueHandler.class,
    HistoryServiceImpl.class
})
@WebMvcTest(
    controllers = { CustomRestController.class}
    )
class CompletedContentTest {
  @Autowired
  MockMvc mockMvc;

  @SpyBean
  HistoryServiceImpl historyService;

  @Captor
  ArgumentCaptor<ContentCachingRequestWrapper> requestArgumentCaptor;

  @Captor
  ArgumentCaptor<HttpServletResponse> responseArgumentCaptor;


  @Test
  void test() throws Exception {
    mockMvc.perform(post("/custom/222")
            .content("{\"title\" : \"title\", \"content\" : \"content\"}")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(
            "{\"url\":\"http://localhost/222\",\"title\":\"title\",\"content\":\"content\"}"));

    verify(historyService, times(1)).insertHistory(requestArgumentCaptor.capture());
    assertThat(new String(requestArgumentCaptor.getValue().getContentAsByteArray())).isEqualTo("{\"title\" : \"title\", \"content\" : \"content\"}");

  }

}