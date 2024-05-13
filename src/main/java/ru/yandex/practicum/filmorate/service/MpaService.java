package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.MpaDao;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MpaService {
  private final MpaDao mpaDao;

  public MpaService(MpaDao mpaDao) {
    this.mpaDao = mpaDao;
  }

  public ArrayList<Mpa> findAll() {
    return mpaDao.findAll();
  }

  public Optional<Mpa> findById(int id) {
    return mpaDao.findById404(id);
  }

  public Optional<Mpa> create(Mpa mpa) {
    return mpaDao.create(mpa);
  }

  public Optional<Mpa> update(Mpa mpa) {
    return mpaDao.update(mpa);
  }

}
