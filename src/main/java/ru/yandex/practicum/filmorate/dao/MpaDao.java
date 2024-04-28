package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.ArrayList;
import java.util.Optional;

public interface MpaDao {

  Optional<Mpa> findById(int id);

  Optional<Mpa> create(Mpa genre);

  ArrayList<Mpa> findAll();

  Optional<Mpa> update(Mpa genre);

}
