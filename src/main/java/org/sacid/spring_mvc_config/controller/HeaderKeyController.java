package org.sacid.spring_mvc_config.controller;

import org.sacid.spring_mvc_config.model.HeaderKey;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/header-key")
@RestController
public class HeaderKeyController {

  @GetMapping
  public String getHeaderKey(HeaderKey headerKey) {
    return headerKey.getKey();
  }
}
