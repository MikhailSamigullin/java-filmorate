package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.ApiError;
import ru.yandex.practicum.filmorate.exception.NotExistsException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("films")
public class FilmController {
  private final ArrayList<Film> films = new ArrayList<>();

  @GetMapping
  public ArrayList<Film> findAll() {
    return films;
  }

  @PostMapping
  public Film create(@RequestBody Film film) {
    films.add(film);
    return film;
  }

  @PutMapping
  public Film update(@RequestBody Film film) throws NotExistsException {
    Optional<Film> filteredFilm = films.stream().filter(item -> item.getId() == film.getId()).findFirst();
    filteredFilm.ifPresent(value -> {
      film.setId(value.getId());
      films.remove(value);
      films.add(film);
    });
    if (filteredFilm.isEmpty()) {
      throw new NotExistsException("Такого id не существует.");
    }
    return film;
  }

  @ExceptionHandler({ ValidationException.class })
  public ResponseEntity<Object> handleException(
          ValidationException ex) {
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), "/films", ex.getLocalizedMessage(), LocalDateTime.now());
    return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
  }

}
