package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class GenreService {
  private final GenreDao genreDao;

  public GenreService(GenreDao genreDao) {
    this.genreDao = genreDao;
  }

  public ArrayList<Genre> findAll() {
    return genreDao.findAll();
  }

  public Optional<Genre> findById(int id) {
    return genreDao.findById(id);
  }

  public Optional<Genre> create(Genre genre) {
    return genreDao.create(genre);
  }

  public Optional<Genre> update(Genre genre) {
    return genreDao.update(genre);
  }

}
