package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.util.ApiError;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.Util;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("films")
public class FilmController {
  private final HashMap<Integer, Film> films = new HashMap<>();

  @GetMapping
  public ArrayList<Film> findAll() {
    return new ArrayList<>(films.values());
  }

  @PostMapping
  public Film create(@Valid @RequestBody Film film) {
    int id = Util.findMaxId(films.keySet()) + 1;
    film.setId(id);
    films.put(id, film);
    return film;
  }

  @PutMapping
  public Film update(@Valid @RequestBody Film film) throws RuntimeException {
    if (films.containsKey(film.getId())) {
      films.put(film.getId(), film);
    } else {
      throw new RuntimeException("Такого id не существует.");
    }
    return film;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationExceptions(
          MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    log.info("Получен запрос к эндпоинту:  \"/films\", ошибки: " + ex.getFieldErrors());
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), "/films", HttpStatus.BAD_REQUEST.getReasonPhrase(), errors, LocalDateTime.now());
    return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
  }

}
