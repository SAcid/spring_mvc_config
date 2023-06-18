package org.sacid.spring_mvc_config.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

  @GetMapping
  public String base(){
    return "OK";
  }

  @GetMapping("/{id}")
  public String id(@PathVariable String id){
    return id;
  }
}
