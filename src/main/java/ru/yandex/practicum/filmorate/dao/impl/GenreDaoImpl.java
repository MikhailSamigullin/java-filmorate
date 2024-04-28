package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.exception.NotExistsException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class GenreDaoImpl implements GenreDao {
  private final JdbcTemplate jdbcTemplate;

  public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Optional<Genre> findById(int id) {
    SqlRowSet genreRow = jdbcTemplate.queryForRowSet("select * from GENRE where GENRE_ID = ?;", id);
    if (!genreRow.next()) {
      throw new NotExistsException("Такого id жанра не существует.");
    }
    Genre genre = buildGenre(genreRow);
    return Optional.of(genre);
  }

  @Override
  public Optional<Genre> create(Genre genre) {
    Genre newGenre;
    String query = "select * from final table (insert into GENRE(NAME) select * from (select '" + genre.getName() + "') x where not exists(select * from GENRE where NAME = '" +  genre.getName() + "'));";
    SqlRowSet genreRow = jdbcTemplate.queryForRowSet(query);
    if (genreRow.next()) {
      newGenre = buildGenre(genreRow);
    } else {
      throw new NotExistsException("Жанр с названием " + genre.getName() + " уже существует.");
    }
    return Optional.of(newGenre);
  }

  @Override
  public ArrayList<Genre> findAll() {
    SqlRowSet genreRow = jdbcTemplate.queryForRowSet("select * from GENRE order by GENRE_ID;");
    ArrayList<Genre> genres = new ArrayList<>();
    while (genreRow.next()) {
      genres.add(buildGenre(genreRow));
    }
    return genres;
  }

  @Override
  public Optional<Genre> update(Genre genre) {
    findById(genre.getId());
    Genre newGenre;
    SqlRowSet genreRow = jdbcTemplate.queryForRowSet(
            "SELECT * FROM final table (update GENRE set NAME = ? where GENRE_ID = ?);",
            genre.getName());
    if (genreRow.next()) {
      newGenre = buildGenre(genreRow);
    } else {
      throw new NotExistsException("Такого id (" + genre.getId() + ") пользователя не существует.");
    }
    return Optional.of(newGenre);
  }

  private Genre buildGenre(SqlRowSet genreRow) {
    return new Genre(
            genreRow.getInt("GENRE_ID"),
            genreRow.getString("NAME")
    );
  }

}
