package ru.yandex.practicum.filmorate.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.util.Util;

import java.time.LocalDate;

import static ru.yandex.practicum.filmorate.util.Util.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Film {
  // После подключения БД, counter будет удален и id будет генерироваться в ней.
  private static int counter;
  private int id;
  private final String name;
  private final String description;
  private final LocalDate releaseDate;
  private final int duration;

  public Film(int id , String name, String description, LocalDate releaseDate, int duration) throws ValidationException {
    this.id = Util.validateId(id, counter);
    this.name = validateFilmName(name);
    this.description = validateDescription(description);
    this.releaseDate = validateReleaseDate(releaseDate);
    this.duration = validateDuration(duration);
    counter++;
  }

}
