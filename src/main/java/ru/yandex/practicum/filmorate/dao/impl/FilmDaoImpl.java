package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.exception.NotExistsException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Component
public class FilmDaoImpl implements FilmDao {
  private final JdbcTemplate jdbcTemplate;

  public FilmDaoImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Optional<Film> findById(int id) {
    SqlRowSet filmRow = jdbcTemplate.queryForRowSet("select f.*, ar.NAME as AGE_RATING_NAME, count(fl.FILM_ID) as RATE from FILM f left join AGE_RATING ar on f.AGE_RATING_ID = ar.AGE_RATING_ID left join FILM_LIKE fl on f.FILM_ID = fl.FILM_ID where f.FILM_ID = ? group by f.FILM_ID;", id);
    if (!filmRow.next()) {
      throw new NotExistsException("Такого id фильма не существует.");
    }
    Film film = buildFilm(filmRow);
    film.setGenres(findGenre(film.getId()));
    return Optional.of(film);
  }

  @Override
  public Optional<Film> create(Film film) {
    SqlRowSet filmRow = jdbcTemplate.queryForRowSet("select FILM_ID from FILM where NAME = ?;", film.getName());
    int filmId = 0;
    if (filmRow.next()) {
      filmId = filmRow.getInt("FILM_ID");
      film.setId(filmId);
      update(film);
    } else {
      jdbcTemplate.update("insert into FILM(NAME,DESCRIPTION, DURATION, RELEASE_DATE, AGE_RATING_ID) values (?,?,?,?,?)",
              film.getName(), film.getDescription(), film.getDuration(), film.getReleaseDate(), film.getMpa().getId());
      SqlRowSet filmRowFinal = jdbcTemplate.queryForRowSet("select FILM_ID from FILM where NAME = ?;", film.getName());
      if (filmRowFinal.next()) {
        filmId = filmRowFinal.getInt("FILM_ID");
      }
    }
    Optional<Film> newFilm = findById(filmId);
    if (film.getGenres() != null) {
      newFilm.ifPresent(value -> addGenre(value, film.getGenres()));
    }
    return findById(filmId);
  }

  @Override
  public ArrayList<Film> findAll() {
    SqlRowSet filmRow = jdbcTemplate.queryForRowSet("select f.*, ar.NAME as AGE_RATING_NAME, count(fl.FILM_ID) as RATE from FILM f left join AGE_RATING ar on f.AGE_RATING_ID = ar.AGE_RATING_ID left join FILM_LIKE fl on f.FILM_ID = fl.FILM_ID group by f.FILM_ID;");
    ArrayList<Film> films = new ArrayList<>();
    while (filmRow.next()) {
      films.add(buildFilm(filmRow));
    }
    return films;
  }

  @Override
  public ArrayList<Film> findTopFilms(int count) {
    SqlRowSet filmRow = jdbcTemplate.queryForRowSet("select f.*, ar.NAME as AGE_RATING_NAME, count(fl.FILM_ID) as RATE from FILM f left join AGE_RATING ar on f.AGE_RATING_ID = ar.AGE_RATING_ID left join FILM_LIKE fl on f.FILM_ID = fl.FILM_ID group by f.FILM_ID order by count(fl.FILM_ID) desc limit ?", count);
    ArrayList<Film> films = new ArrayList<>();
    while (filmRow.next()) {
      films.add(buildFilm(filmRow));
    }
    return films;
  }

  @Override
  public Optional<Film> update(Film film) {
    jdbcTemplate.update("update FILM set NAME=?, DESCRIPTION = ?, DURATION = ?, RELEASE_DATE = ?, AGE_RATING_ID = ? where FILM_ID = ?;",
            film.getName(), film.getDescription(), film.getDuration(), film.getReleaseDate(), film.getMpa().getId(), film.getId());
    return findById(film.getId());
  }

  @Override
  public Optional<Film> addLike(int id, int userId) {
    SqlRowSet filmRow = jdbcTemplate.queryForRowSet("select * from FILM_LIKE where FILM_ID = ? and USER_ID = ?;",
            id, userId);
    if (!filmRow.next()) {
      jdbcTemplate.update(
              "insert into FILM_LIKE (FILM_ID, USER_ID) values(?, ?);",
              id, userId);
    }
    return findById(id);
  }

  @Override
  public Optional<Film> removeLike(int id, int userId) {
    jdbcTemplate.update(
            "delete from FILM_LIKE where FILM_ID = ? and USER_ID = ?;",
            id, userId);
    return findById(id);
  }

  private ArrayList<Genre> findGenre(int id) {
    SqlRowSet filmRow = jdbcTemplate.queryForRowSet("select g.GENRE_ID, g.NAME from FILM f join FILM_GENRE fg on fg.FILM_ID = f.FILM_ID left join GENRE g on fg.GENRE_ID = g.GENRE_ID where f.FILM_ID = ?;", id);
    ArrayList<Genre> genres = new ArrayList<>();
    while (filmRow.next()) {
      genres.add(new Genre(filmRow.getInt("GENRE_ID"), filmRow.getString("NAME")));
    }
    return genres;
  }

  private void addGenre(Film film, ArrayList<Genre> genres) {
    jdbcTemplate.update("delete from FILM_GENRE where FILM_ID = ?;",
            film.getId());
    if (genres == null) {
      return;
    }
    for (Genre genre : genres) {
      SqlRowSet filmRow = jdbcTemplate.queryForRowSet("select * from FILM_GENRE where FILM_ID = ? and GENRE_ID = ?;",
              film.getId(), genre.getId());
      if (filmRow.next()) {
        continue;
      }
      SqlRowSet filmRowFinal = jdbcTemplate.queryForRowSet("select * from GENRE where GENRE_ID = ?;",
              genre.getId());
      if (!filmRowFinal.next()) {
        throw new ValidationException("Такого id жанра не существует.");
      }
      jdbcTemplate.update("insert into FILM_GENRE(FILM_ID, GENRE_ID) values(?, ?);",
              film.getId(), genre.getId());
    }
  }

  private Film buildFilm(SqlRowSet filmRow) {
    return Film.builder()
            .id(filmRow.getInt("FILM_ID"))
            .name(filmRow.getString("NAME"))
            .description(filmRow.getString("DESCRIPTION"))
            .releaseDate(LocalDate.parse(Objects.requireNonNull(filmRow.getString("RELEASE_DATE")).substring(0, 10)))
            .duration(filmRow.getInt("DURATION"))
            .mpa(new Mpa(filmRow.getInt("AGE_RATING_ID"), filmRow.getString("AGE_RATING_NAME")))
            .rate(filmRow.getInt("RATE"))
            .build();
  }

}
