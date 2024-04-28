package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("genres")
public class GenreController {
  private final GenreService genreService;

  @Autowired
  public GenreController(GenreService genreService) {
    this.genreService = genreService;
  }

  @GetMapping
  public ArrayList<Genre> findAll() {
    return genreService.findAll();
  }

  @GetMapping("{id}")
  public Optional<Genre> findById(@PathVariable String id) {
    return genreService.findById(Integer.parseInt(id));
  }

  @PostMapping
  public Optional<Genre> create(@Valid @RequestBody Genre genre) {
    return genreService.create(genre);
  }

  @PutMapping
  public Optional<Genre> update(@Valid @RequestBody Genre genre) {
    return genreService.update(genre);
  }

}
