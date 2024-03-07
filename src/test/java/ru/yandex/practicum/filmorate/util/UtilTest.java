package ru.yandex.practicum.filmorate.util;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

  @Test
  void validateFilmName() throws ValidationException {
    assertEquals(Util.validateFilmName("Film name"), "Film name");
    assertThrows(ValidationException.class, () -> Util.validateFilmName(""));
  }

  @Test
  void validateDescription() throws ValidationException {
    assertEquals(Util.validateDescription("Film description"), "Film description");
    assertThrows(ValidationException.class, () -> Util.validateDescription("Film description Film description Film description Film description " +
            "Film description Film description Film description Film description Film description " +
            "Film description Film description Film description Film description Film description "));
  }

  @Test
  void validateReleaseDate() throws ValidationException {
    LocalDate date = LocalDate.parse("2024-01-01", Util.SHORT_DATE_FORMAT);
    LocalDate invalidDate = LocalDate.parse("1895-12-27", Util.SHORT_DATE_FORMAT);
    assertEquals(Util.validateReleaseDate(date), date);
    assertThrows(ValidationException.class, () -> Util.validateReleaseDate(invalidDate));
  }

  @Test
  void validateDuration() throws ValidationException {
    assertEquals(Util.validateDuration(100), 100);
    assertThrows(ValidationException.class, () -> Util.validateDuration(-100));
  }

  @Test
  void validateEmail() throws ValidationException {
    String Email = "username@domain.com";
    String wrongEmail1 = "";
    String wrongEmail2 = "username/@domain.com";
    String wrongEmail3 = ".user.name@domain.com";
    String wrongEmail4 = "user-name@domain.com.";
    String wrongEmail5 = "username@.com";
    assertEquals(Util.validateEmail(Email), Email);
    assertThrows(ValidationException.class, () -> Util.validateEmail(wrongEmail1));
    assertThrows(ValidationException.class, () -> Util.validateEmail(wrongEmail2));
    assertThrows(ValidationException.class, () -> Util.validateEmail(wrongEmail3));
    assertThrows(ValidationException.class, () -> Util.validateEmail(wrongEmail4));
    assertThrows(ValidationException.class, () -> Util.validateEmail(wrongEmail5));
  }

  @Test
  void validateName() {
    String name = "Name";
    String emptyName = "";
    String login = "Login";
    assertEquals(Util.validateName(name, login), name);
    assertEquals(Util.validateName(emptyName, login), login);
  }

  @Test
  void validateLogin() throws ValidationException {
    String emptyLogin = "";
    String login = "Login";
    String loginWithSpace = "Log in";
    assertEquals(Util.validateLogin(login), login);
    assertThrows(ValidationException.class, () -> Util.validateLogin(emptyLogin));
    assertThrows(ValidationException.class, () -> Util.validateLogin(loginWithSpace));
  }

  @Test
  void validateBirthday() throws ValidationException {
    LocalDate date = LocalDate.parse("2024-01-01", Util.SHORT_DATE_FORMAT);
    LocalDate invalidDate = LocalDate.parse("2100-12-27", Util.SHORT_DATE_FORMAT);
    assertEquals(Util.validateBirthday(date), date);
    assertThrows(ValidationException.class, () -> Util.validateBirthday(invalidDate));
  }
}