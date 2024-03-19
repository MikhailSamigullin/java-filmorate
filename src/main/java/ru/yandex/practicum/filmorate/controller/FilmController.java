package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.Util;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("films")
public class FilmController {
  private final HashMap<Integer, Film> films = new HashMap<>();

  @GetMapping
  public ArrayList<Film> findAll() {
    return new ArrayList<>(films.values());
  }

  @PostMapping
  public Film create(@Valid @RequestBody Film film) {
    int id = Util.findMaxId(films.keySet()) + 1;
    film.setId(id);
    films.put(id, film);
    return film;
  }

  @PutMapping
  public Film update(@Valid @RequestBody Film film) {
    if (films.containsKey(film.getId())) {
      films.put(film.getId(), film);
    } else {
      throw new RuntimeException("Такого id не существует.");
    }
    return film;
  }

}
