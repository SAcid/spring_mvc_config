package org.sacid.spring_mvc_config.controller;

import lombok.RequiredArgsConstructor;
import org.sacid.spring_mvc_config.model.CustomRequest;
import org.sacid.spring_mvc_config.model.CustomResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CustomRestController {
  @PostMapping("/custom/{id}")
  public CustomResponse custom(@PathVariable String id, @RequestBody CustomRequest customRequest) {
    return CustomResponse.of(customRequest, id);
  }
}
