package ru.yandex.practicum.filmorate.exception;

import lombok.experimental.StandardException;

@StandardException
public class ValidationException extends RuntimeException {
  public ValidationException(String message) {
    super(message);
  }
}