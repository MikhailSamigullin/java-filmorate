package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.Optional;

public interface FilmDao {

  Optional<Film> findById(int id);

  Optional<Film> create(Film film);

  ArrayList<Film> findAll();

  ArrayList<Film> findTopFilms(int count);

  Optional<Film> update(Film film);

  Optional<Film> addLike(int id, int userId);

  Optional<Film> removeLike(int id, int userId);

}
