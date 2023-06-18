package org.sacid.spring_mvc_config.model;

import org.sacid.spring_mvc_config.annotation.CustomValue;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@CustomValue
@EqualsAndHashCode
@Getter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomResponse {
  @CustomValue
  private final String url;
  private final String title;
  private final String content;
  public static CustomResponse of(CustomRequest customRequest, String url) {
    return new CustomResponse(url, customRequest.getTitle(), customRequest.getContent());
  }
}
