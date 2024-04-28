package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.MpaDao;
import ru.yandex.practicum.filmorate.exception.NotExistsException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class MpaDaoImpl implements MpaDao {
  private final JdbcTemplate jdbcTemplate;

  public MpaDaoImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Optional<Mpa> findById(int id) {
    SqlRowSet mpaRow = jdbcTemplate.queryForRowSet("select * from AGE_RATING where AGE_RATING_ID = ?;", id);
    if (!mpaRow.next()) {
      throw new NotExistsException("Такого id рейтинга не существует.");
    }
    Mpa mpa = buildMpa(mpaRow);
    return Optional.of(mpa);
  }

  @Override
  public Optional<Mpa> create(Mpa mpa) {
    Mpa newMpa;
    String query = "select * from final table (insert into AGE_RATING(NAME) select * from (select '" + mpa.getName() + "') x where not exists(select * from AGE_RATING where NAME = '" +  mpa.getName() + "'));";
    SqlRowSet mpaRow = jdbcTemplate.queryForRowSet(query);
    if (mpaRow.next()) {
      newMpa = buildMpa(mpaRow);
    } else {
      throw new NotExistsException("Жанр с названием " + mpa.getName() + " уже существует.");
    }
    return Optional.of(newMpa);
  }

  @Override
  public ArrayList<Mpa> findAll() {
    SqlRowSet mpaRow = jdbcTemplate.queryForRowSet("select * from AGE_RATING order by AGE_RATING_ID;");
    ArrayList<Mpa> mpas = new ArrayList<>();
    while (mpaRow.next()) {
      mpas.add(buildMpa(mpaRow));
    }
    return mpas;
  }

  @Override
  public Optional<Mpa> update(Mpa mpa) {
    findById(mpa.getId());
    Mpa newMpa;
    SqlRowSet mpaRow = jdbcTemplate.queryForRowSet(
            "SELECT * FROM final table (update AGE_RATING set NAME = ? where AGE_RATING_ID = ?);",
            mpa.getName());
    if (mpaRow.next()) {
      newMpa = buildMpa(mpaRow);
    } else {
      throw new NotExistsException("Такого id (" + mpa.getId() + ") рейтинга не существует.");
    }
    return Optional.of(newMpa);
  }

  private Mpa buildMpa(SqlRowSet mpaRow) {
    return new Mpa(
            mpaRow.getInt("AGE_RATING_ID"),
            mpaRow.getString("NAME")
    );
  }

}
