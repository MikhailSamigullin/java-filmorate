package ru.yandex.practicum.filmorate.util;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Map;

@ToString
@Getter
@EqualsAndHashCode
public class ApiError {
  private final int status;
  private final String path;
  private final String error;
  private final Map<String, String> message;
  private final LocalDateTime timestamp;

  public ApiError(int status, String path, String error, Map<String, String> message, LocalDateTime timestamp) {
    this.status = status;
    this.path = path;
    this.error = error;
    this.message = message;
    this.timestamp = timestamp;
  }
}
