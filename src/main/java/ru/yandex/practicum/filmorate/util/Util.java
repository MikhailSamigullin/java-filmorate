package ru.yandex.practicum.filmorate.util;

import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Util {

  public static int validateId(int id, int counter) throws ValidationException {
    if (id > counter) {
      throw new ValidationException("Такого id не существует.");
    }
    if (id == 0) {
      return counter;
    }
    return id;
  }

  public static DateTimeFormatter SHORT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public static String validateFilmName(String name) throws ValidationException {
    if (name.isEmpty()) {
      throw new ValidationException("Название не может быть пустым.");
    }
    return name;
  }

  public static String validateDescription(String description) throws ValidationException {
    if (description.length() > 200) {
      throw new ValidationException("Максимальная длина описания — 200 символов.");
    }
    return description;
  }

  public static LocalDate validateReleaseDate(LocalDate releaseDate) throws ValidationException {
    LocalDate date = LocalDate.parse("1895-12-28", Util.SHORT_DATE_FORMAT);
    if (releaseDate.isBefore(date)) {
      throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года.");
    }
    return releaseDate;
  }

  public static int validateDuration(int duration) throws ValidationException {
    if (duration < 0) {
      throw new ValidationException("Продолжительность фильма должна быть положительной.");
    }
    return duration;
  }

  public static String validateEmail(String email) throws ValidationException {
    String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    if (!email.matches(regexPattern)) {
      throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @.");
    }
    return email;
  }

  public static String validateName(String name, String login) {
    if (name == null || name.isEmpty()) {
      return login;
    }
    return name;
  }

  public static String validateLogin(String login) throws ValidationException {
    if (login.isEmpty() || login.contains(" ")) {
      throw new ValidationException("Логин не может быть пустым и содержать пробелы.");
    }
    return login;
  }

  public static LocalDate validateBirthday(LocalDate birthday) throws ValidationException {
    LocalDate date = LocalDate.now();
    if (birthday.isAfter(date)) {
      throw new ValidationException("Дата рождения не может быть в будущем.");
    }
    return birthday;
  }

}
