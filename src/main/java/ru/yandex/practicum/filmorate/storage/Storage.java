package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;
import java.util.Optional;

public interface Storage<T> {
  T create(T object);

  Optional<T> findById(int id);

  T update(T object);

  Collection<T> findAll();
}
