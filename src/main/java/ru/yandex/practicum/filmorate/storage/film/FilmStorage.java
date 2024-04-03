package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;

public interface FilmStorage {

  Film add(Film film);

  Film update(Film film);

  ArrayList<Film> findAll();

  ArrayList<Film> findTopFilms(String count);

  Film addLike(int id, int userId);

  Film removeLike(int id, int userId);

  void checkFilmId(Film film);

  void checkFilmId(int id);
}
