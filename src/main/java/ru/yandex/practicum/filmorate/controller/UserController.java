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
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("users")
public class UserController {
  private final ArrayList<User> users = new ArrayList<>();

  @GetMapping
  public ArrayList<User> findAll() {
    return users;
  }

  @PostMapping
  public User create(@RequestBody User user) {
    users.add(user);
    return user;
  }

  @PutMapping
  public User update(@RequestBody User user) {
    Optional<User> filteredFilm = users.stream().filter(item -> item.getId() == user.getId()).findFirst();
    filteredFilm.ifPresent(value -> {
      user.setId(value.getId());
      users.remove(value);
      users.add(user);
    });
    return user;
  }

  @ExceptionHandler({ ValidationException.class })
  public ResponseEntity<Object> handleException(
          ValidationException ex) {
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), "/users", ex.getLocalizedMessage(), LocalDateTime.now());
    return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
  }

}
