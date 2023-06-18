package org.sacid.spring_mvc_config.model;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HeaderKey {
  private static final String DEFAULT = "";
  private final String key;

  public static HeaderKey of(String key) {
    return new HeaderKey(Objects.requireNonNullElse(key, DEFAULT));
  }
}
