package org.sacid.spring_mvc_config.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.sacid.spring_mvc_config.service.HistoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@Import({HistoryServiceImpl.class})
@WebMvcTest(AuthController.class)
class AuthControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  public void base_ok() throws Exception {
    mockMvc.perform(get("/auth")
            .header("authentication-id", "test")
        )
        .andExpect(status().isOk())
        .andExpect(content().string("OK"));
  }

  @Test
  public void base_is_authorize() throws Exception {
    mockMvc.perform(get("/auth")
        )
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void id_ok() throws Exception {
    mockMvc.perform(get("/auth/abc")
            .header("authentication-id", "test")
        )
        .andExpect(status().isOk())
        .andExpect(content().string("abc"));

  }

  @Test
  public void id_is_authorize() throws Exception {
    mockMvc.perform(get("/auth/abc")
        )
        .andExpect(status().isUnauthorized());
  }
}