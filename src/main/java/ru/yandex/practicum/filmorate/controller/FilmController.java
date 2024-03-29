package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("films")
public class FilmController {
  private final FilmService filmService;

  @Autowired
  public FilmController(FilmService filmService) {
    this.filmService = filmService;
  }

  @GetMapping
  public ArrayList<Film> findAll() {
    return filmService.findAll();
  }

  @GetMapping("/popular")
  public ArrayList<Film> findTopFilms(@RequestParam("count") Optional<String> count) {
    return filmService.findTopFilms(count.orElse("10"));
  }

  @PostMapping
  public Film create(@Valid @RequestBody Film film) {
    return filmService.create(film);
  }

  @PutMapping
  public Film update(@Valid @RequestBody Film film) {
    return filmService.update(film);
  }

  @PutMapping("/{id}/like/{userId}")
  public Film addLike(@PathVariable int id, @PathVariable int userId) {
    return filmService.addLike(id, userId);
  }

  @DeleteMapping("/{id}/like/{userId}")
  public Film deleteLike(@PathVariable int id, @PathVariable int userId) {
    return filmService.deleteLike(id, userId);
  }
}
