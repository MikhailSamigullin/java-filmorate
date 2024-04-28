package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.ArrayList;
import java.util.Optional;

public interface GenreDao {

  Optional<Genre> findById(int id);

  Optional<Genre> create(Genre genre);

  ArrayList<Genre> findAll();

  Optional<Genre> update(Genre genre);

}
