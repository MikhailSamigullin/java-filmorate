package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("mpa")
public class MpaController {
  private final MpaService mpaService;

  @Autowired
  public MpaController(MpaService mpaService) {
    this.mpaService = mpaService;
  }

  @GetMapping
  public ArrayList<Mpa> findAll() {
    return mpaService.findAll();
  }

  @GetMapping("{id}")
  public Optional<Mpa> findById(@PathVariable String id) {
    return mpaService.findById(Integer.parseInt(id));
  }

  @PostMapping
  public Optional<Mpa> create(@Valid @RequestBody Mpa mpa) {
    return mpaService.create(mpa);
  }

  @PutMapping
  public Optional<Mpa> update(@Valid @RequestBody Mpa mpa) {
    return mpaService.update(mpa);
  }

}
