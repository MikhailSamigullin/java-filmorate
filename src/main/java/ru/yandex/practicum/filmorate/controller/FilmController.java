package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

  @GetMapping("{id}")
  public Optional<Film> findById(@PathVariable String id) {
    return filmService.findById(id);
  }

  @GetMapping("/popular")
  public ArrayList<Film> findTopFilms(@RequestParam(name = "count", required = false, defaultValue = "10") String count) {
    return filmService.findTopFilms(count);
  }

  @PostMapping
  public Optional<Film> create(@Valid @RequestBody Film film) {
    return filmService.create(film);
  }

  @PutMapping
  public Optional<Film> update(@Valid @RequestBody Film film) {
    return filmService.update(film);
  }

  @PutMapping("/{id}/like/{userId}")
  public Optional<Film> addLike(@PathVariable int id, @PathVariable int userId) {
    return filmService.addLike(id, userId);
  }

  @DeleteMapping("/{id}/like/{userId}")
  public Optional<Film> deleteLike(@PathVariable int id, @PathVariable int userId) {
    return filmService.deleteLike(id, userId);
  }
}
