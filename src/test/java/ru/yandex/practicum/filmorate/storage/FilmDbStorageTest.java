package ru.yandex.practicum.filmorate.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FilmDbStorageTest {
  private final FilmDao filmStorage;
  private Mpa mpa;

  @Autowired
  FilmDbStorageTest(FilmDao filmStorage) {
    this.filmStorage = filmStorage;
  }

  @BeforeEach
  void setUp() {
    mpa = new Mpa(1, "qwewe");
  }

  @Test
  public void testFindById() {
    Film film = Film.builder()
            .id(1)
            .name("awawdawdwd")
            .description("arsgrdgrg")
            .releaseDate(LocalDate.of(1990, 1, 1))
            .duration(90)
            .mpa(mpa)
            .build();
    Optional<Film> film1 = filmStorage.create(film);
    Optional<Film> film2 = filmStorage.findById(film.getId());
    if (film1.isPresent() && film2.isPresent()) {
      assertEquals(film1.get().getId(), film2.get().getId());
    }
  }

  @Test
  public void testFindAll() {
    Film film1 = Film.builder()
            .id(1)
            .name("awawdawdwd")
            .description("arsgrdgrg")
            .releaseDate(LocalDate.of(1990, 1, 1))
            .duration(90)
            .mpa(mpa)
            .build();
    filmStorage.create(film1);
    Film film2 = Film.builder()
            .id(2)
            .name("awawdawdwd2")
            .description("arsgrdgrg")
            .releaseDate(LocalDate.of(1990, 1, 1))
            .duration(90)
            .mpa(mpa)
            .build();
    filmStorage.create(film2);
    ArrayList<Film> films = new ArrayList<>();
    films.add(film1);
    films.add(film2);
    ArrayList<Film> createdFilms = filmStorage.findAll();
    assertEquals(films.size(), createdFilms.size());
  }

  @Test
  public void testCreate() {
    Film film = Film.builder()
            .id(1)
            .name("awawdawdwd")
            .description("arsgrdgrg")
            .releaseDate(LocalDate.of(1990, 1, 1))
            .duration(90)
            .mpa(mpa)
            .build();
    Optional<Film> film1 = filmStorage.create(film);
    Optional<Film> film2 = filmStorage.findById(film.getId());
    if (film1.isPresent() && film2.isPresent()) {
      assertEquals(film1.get().getId(), film2.get().getId());
    }
  }

  @Test
  public void testUpdate() {
    Film film = Film.builder()
            .id(1)
            .name("awawdawdwd")
            .description("arsgrdgrg")
            .releaseDate(LocalDate.of(1990, 1, 1))
            .duration(90)
            .mpa(mpa)
            .build();
    Optional<Film> film1 = filmStorage.create(film);
    Optional<Film> film2 = filmStorage.findById(film.getId());
    if (film1.isPresent() && film2.isPresent()) {
      assertEquals(film1.get().getId(), film2.get().getId());
    }
    Film newFilm = Film.builder()
            .id(1)
            .name("awawdawdwd4")
            .description("arsgrdgrg")
            .releaseDate(LocalDate.of(1990, 1, 1))
            .duration(90)
            .mpa(mpa)
            .build();
    Optional<Film> film3 = filmStorage.update(newFilm);
    Optional<Film> film4 = filmStorage.findById(newFilm.getId());
    if (film3.isPresent() && film4.isPresent()) {
      assertEquals(film3.get(), film4.get());
    }
  }
}
