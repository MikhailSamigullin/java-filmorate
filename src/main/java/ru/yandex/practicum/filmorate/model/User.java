package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.BirthdayDateConstraint;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class User {
  private int id;
  @Email(message = "Неверный формат email.")
  private final String email;
  @NotEmpty(message = "Логин не должен быть пустым.")
  @Pattern(regexp = "^\\S+$", message = "Логин не должен содержать пробелов.")
  private final String login;
  private String name;
  @BirthdayDateConstraint(message = "Дата рождения не может быть в будущем.")
  private final LocalDate birthday;
  private final Set<Integer> friends = new HashSet<>();
}
