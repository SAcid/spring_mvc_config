package org.sacid.spring_mvc_config.model;

import org.sacid.spring_mvc_config.annotation.CustomValue;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class CustomRequest {
  @CustomValue
  private final String key;
  private final String title;
  private final String content;
}
