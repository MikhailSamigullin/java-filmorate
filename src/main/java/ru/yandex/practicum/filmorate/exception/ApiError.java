package ru.yandex.practicum.filmorate.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@EqualsAndHashCode
public class ApiError {
  private final int status;
  private final String path;
  private final String error;
  private final LocalDateTime timestamp;

  public ApiError(int status, String path, String error, LocalDateTime timestamp) {
    this.status = status;
    this.path = path;
    this.error = error;
    this.timestamp = timestamp;
  }
}
