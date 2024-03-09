package ru.yandex.practicum.filmorate.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.util.Util;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {
  private static int counter = 1;
  private int id;
  private final String email;
  private final String login;
  private String name;
  private final LocalDate birthday;

  public User(int id, String email, String login, String name, LocalDate birthday) throws ValidationException {
    this.id = Util.validateId(id, counter);
    this.email = Util.validateEmail(email);
    this.login = Util.validateLogin(login);
    this.name = Util.validateName(name, login);
    this.birthday = Util.validateBirthday(birthday);
    counter++;
  }

}
