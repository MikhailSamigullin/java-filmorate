package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.util.Util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class FilmService {
  private final InMemoryFilmStorage inMemoryFilmStorage;

  public FilmService(InMemoryFilmStorage inMemoryFilmStorage) {
    this.inMemoryFilmStorage = inMemoryFilmStorage;
  }

  public ArrayList<Film> findAll() {
    return new ArrayList<>(inMemoryFilmStorage.films.values());
  }

  public ArrayList<Film> findTopFilms(String count) {
    int filmCount = count.matches("\\d+") ? Integer.parseInt(count) : 10;
    return inMemoryFilmStorage.films.values()
            .stream()
            .sorted(Comparator.comparing(film -> film.getLikes().size(), Comparator.reverseOrder()))
            .limit(filmCount)
            .collect(Collectors.toCollection(ArrayList::new));
  }

  public Film create(Film film) {
    int id = Util.findMaxId(inMemoryFilmStorage.films.keySet()) + 1;
    film.setId(id);
    inMemoryFilmStorage.films.put(id, film);
    return film;
  }

  public Film update(Film film) {
    checkFilmId(film.getId());
    inMemoryFilmStorage.films.put(film.getId(), film);
    return film;
  }

  public Film addLike(int id, int userId) {
    checkFilmId(id);
    inMemoryFilmStorage.films.get(id).getLikes().add(userId);
    return inMemoryFilmStorage.films.get(id);
  }

  public Film deleteLike(int id, int userId) {
    checkFilmId(id);
    Film film = inMemoryFilmStorage.films.get(id);
    film.getLikes().remove(userId);
    return film;
  }

  private void checkFilmId(int id) {
    if (!inMemoryFilmStorage.films.containsKey(id)) {
      throw new RuntimeException("Такого id не существует.");
    }
  }
}
