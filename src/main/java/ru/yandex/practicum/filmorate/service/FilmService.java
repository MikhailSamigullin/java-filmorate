package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.dao.MpaDao;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class FilmService {
  private final FilmDao filmDao;
  private final UserDao userDao;
  private final MpaDao mpaDao;

  public FilmService(FilmDao filmDao, UserDao userDao, MpaDao mpaDao) {
    this.filmDao = filmDao;
    this.userDao = userDao;
    this.mpaDao = mpaDao;
  }

  public ArrayList<Film> findAll() {
    return filmDao.findAll();
  }

  public Optional<Film> findById(String id) {
    return filmDao.findById(Integer.parseInt(id));
  }

  public ArrayList<Film> findTopFilms(String count) {
    int filmCount = count.matches("\\d+") ? Integer.parseInt(count) : 10;
    return filmDao.findTopFilms(filmCount);
  }

  public Optional<Film> create(Film film) {
    mpaDao.findById400(film.getMpa().getId());
    return filmDao.create(film);
  }

  public Optional<Film> update(Film film) {
    filmDao.findById(film.getId());
    return filmDao.update(film);
  }

  public Optional<Film> addLike(int id, int userId) {
    filmDao.findById(id);
    userDao.findUserById(String.valueOf(userId));
    return filmDao.addLike(id, userId);
  }

  public Optional<Film> deleteLike(int id, int userId) {
    filmDao.findById(id);
    userDao.findUserById(String.valueOf(userId));
    return filmDao.removeLike(id, userId);
  }

}
