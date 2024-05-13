package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.ReleaseDateConstraint;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class Film {
  private int id;
  @NotEmpty(message = "Название не может быть пустым.")
  private final String name;
  @Size(max = 200, message = "Максимальная длина описания: {max} символов.")
  private final String description;
  @ReleaseDateConstraint(message = "Дата релиза должна быть не раньше 28 декабря 1895 года.")
  private final LocalDate releaseDate;
  @Min(value = 1, message = "Минимальная продолжительность(минуты): {value}.")
  private final int duration;
  private int rate;
  private Mpa mpa;
  @Builder.Default
  private ArrayList<Genre> genres = new ArrayList<>();
  private final Set<Integer> likes = new HashSet<>();

  public void addGenres(Genre genre) {
    genres.add(genre);
  }
}
