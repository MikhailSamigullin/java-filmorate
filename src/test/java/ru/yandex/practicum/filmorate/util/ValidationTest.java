package ru.yandex.practicum.filmorate.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ValidationTest {

  private static Validator validator;

  @BeforeEach
  public void setupValidatorInstance() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Test
  public void whenNoConstraintViolations() {
    User user = new User(0, "mail@mail.ru", "dolore", "Nick Name", LocalDate.of(1946, 8, 20));
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  public void whenEmailConstraintViolations() {
    User user = new User(0, "mailmail.ru", "dolore", "Nick Name", LocalDate.of(1946, 8, 20));
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  public void whenLoginConstraintViolations() {
    User user = new User(0, "mail@mail.ru", "dolore dolore", "Nick Name", LocalDate.of(1946, 8, 20));
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  public void whenBirthdayConstraintViolations() {
    User user = new User(0, "mail@mail.ru", "dolore", "Nick Name", LocalDate.of(2222, 8, 20));
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  public void whenEmptyNameConstraintViolations() {
    Film film = new Film(0, "", "adipisicing", LocalDate.of(1967, 3, 25), 100);
    Set<ConstraintViolation<Film>> violations = validator.validate(film);
    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  public void whenMaxSizeDescriptionConstraintViolations() {
    String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
            "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation " +
            "ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
            "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non " +
            "proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    Film film = new Film(0, "nisi eiusmod", description, LocalDate.of(1967, 3, 25), 100);
    Set<ConstraintViolation<Film>> violations = validator.validate(film);
    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  public void whenReleaseDateConstraintViolations() {
    Film film = new Film(0, "nisi eiusmod", "adipisicing", LocalDate.of(1800, 3, 25), 100);
    Set<ConstraintViolation<Film>> violations = validator.validate(film);
    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  public void whenDurationConstraintViolations() {
    Film film = new Film(0, "nisi eiusmod", "adipisicing", LocalDate.of(1967, 3, 25), 0);
    Set<ConstraintViolation<Film>> violations = validator.validate(film);
    assertThat(violations.size()).isEqualTo(1);
  }

}