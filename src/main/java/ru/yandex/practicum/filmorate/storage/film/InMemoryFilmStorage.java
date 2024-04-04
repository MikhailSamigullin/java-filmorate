package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotExistsException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.Util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {
  private final HashMap<Integer, Film> films = new HashMap<>();

  @Override
  public Film add(Film film) {
    int id = Util.findMaxId(films.keySet()) + 1;
    film.setId(id);
    films.put(id, film);
    return film;
  }

  @Override
  public Film update(Film film) {
    films.put(film.getId(), film);
    return film;
  }

  @Override
  public ArrayList<Film> findAll() {
    return new ArrayList<>(films.values());
  }

  @Override
  public ArrayList<Film> findTopFilms(int filmCount) {
    return films.values()
            .stream()
            .sorted(Comparator.comparing(film -> film.getLikes().size(), Comparator.reverseOrder()))
            .limit(filmCount)
            .collect(Collectors.toCollection(ArrayList::new));
  }

  @Override
  public Film addLike(int id, int userId) {
    films.get(id).getLikes().add(userId);
    return films.get(id);
  }

  @Override
  public Film removeLike(int id, int userId) {
    Film film = getFilm(id);
    film.getLikes().remove(userId);
    return film;
  }

  @Override
  public void checkFilmId(int id) {
    if (!films.containsKey(id)) {
      throw new NotExistsException("Такого id фильма не существует.");
    }
  }

  private Film getFilm(int id) {
    if (!films.containsKey(id)) {
      throw new NotExistsException("Такого id фильма не существует.");
    }
    return films.get(id);
  }

}
