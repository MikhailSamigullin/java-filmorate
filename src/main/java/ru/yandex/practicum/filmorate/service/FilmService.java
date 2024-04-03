package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;

@Service
public class FilmService {
  private final FilmStorage filmStorage;
  private final UserStorage userStorage;

  public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
    this.filmStorage = filmStorage;
    this.userStorage = userStorage;
  }

  public ArrayList<Film> findAll() {
    return filmStorage.findAll();
  }

  public ArrayList<Film> findTopFilms(String count) {
    int filmCount = count.matches("\\d+") ? Integer.parseInt(count) : 10;
    return filmStorage.findTopFilms(String.valueOf(filmCount));
  }

  public Film create(Film film) {
    return filmStorage.add(film);
  }

  public Film update(Film film) {
    filmStorage.checkFilmId(film);
    return filmStorage.update(film);
  }

  public Film addLike(int id, int userId) {
    filmStorage.checkFilmId(id);
    userStorage.checkUserId(userId);
    return filmStorage.addLike(id, userId);
  }

  public Film deleteLike(int id, int userId) {
    filmStorage.checkFilmId(id);
    userStorage.checkUserId(userId);
    return filmStorage.removeLike(id, userId);
  }

}
