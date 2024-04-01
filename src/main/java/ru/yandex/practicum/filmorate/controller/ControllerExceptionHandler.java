package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.util.ApiError;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  public ResponseEntity<Object> handleValidationExceptions(
          MethodArgumentNotValidException exception, HttpServletRequest request) {
    Map<String, String> errors = new HashMap<>();
    exception.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    log.info("Получен запрос к эндпоинту:  \"" + request.getRequestURI() + "\", ошибки: " + exception.getFieldErrors());
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), HttpStatus.BAD_REQUEST.getReasonPhrase(), errors, LocalDateTime.now());
    return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
  }

}
