package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.exception.NotExistsException;
import ru.yandex.practicum.filmorate.model.Follower;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao {
  private final JdbcTemplate jdbcTemplate;

  public UserDaoImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Optional<User> findUserById(String id) {
    SqlRowSet userRow = jdbcTemplate.queryForRowSet("select * from \"user\" where USER_ID = ?;", id);
    if (!userRow.next()) {
      throw new NotExistsException("Такого id пользователя не существует.");
    }
    User user = buildUser(userRow);
    return Optional.of(user);
  }

  @Override
  public Optional<User> create(User user) {
    String query = "select * from final table (insert into \"user\"(NAME,LOGIN, EMAIL, BIRTHDAY) select * from (select '" + user.getName() + "', '" +  user.getLogin() + "', '" + user.getEmail() + "', '" + user.getBirthday() + "') x where not exists(select * from \"user\" where LOGIN = '" +  user.getLogin() + "'));";
    SqlRowSet userRow = jdbcTemplate.queryForRowSet(query);
    if (!userRow.next()) {
      throw new NotExistsException("Пользователь с логином " + user.getLogin() + " уже существует.");
    }
    return Optional.of(buildUser(userRow));
  }

  @Override
  public ArrayList<User> findAll() {
    SqlRowSet userRow = jdbcTemplate.queryForRowSet("select * from \"user\";");
    ArrayList<User> users = new ArrayList<>();
    while (userRow.next()) {
      users.add(buildUser(userRow));
    }
    return users;
  }

  @Override
  public Optional<User> update(User user) {
    findUserById(String.valueOf(user.getId()));
    SqlRowSet userRow = jdbcTemplate.queryForRowSet(
            "SELECT * FROM final table (update \"user\" set NAME=?, LOGIN = ?, EMAIL = ?, BIRTHDAY = ? where USER_ID = ?);",
            user.getName(), user.getEmail(), user.getLogin(), user.getBirthday(), user.getId());
    if (!userRow.next()) {
      throw new NotExistsException("Такого id (" + user.getId() + ") пользователя не существует.");
    }
    return Optional.of(buildUser(userRow));
  }

  @Override
  public ArrayList<Follower> addFriend(int id, int friendId) {
    SqlRowSet userRow = jdbcTemplate.queryForRowSet("select * from FOLLOWER where FOLLOWING_USER_ID = ? and FOLLOWED_USER_ID = ?;",
            id, friendId);
    if (!userRow.next()) {
      jdbcTemplate.update(
              "insert into FOLLOWER (FOLLOWING_USER_ID, FOLLOWED_USER_ID) values(?, ?);",
              id, friendId);
    }
    SqlRowSet userRowFinal = jdbcTemplate.queryForRowSet("select * from FOLLOWER where FOLLOWING_USER_ID = ? and FOLLOWED_USER_ID = ?;",
            id, friendId);
    ArrayList<Follower> userFriends = new ArrayList<>();
    if (userRowFinal.next()) {
      userFriends.add(buildFollower(userRowFinal));
    }
    return userFriends;
  }

  @Override
  public ArrayList<Follower> findFriends(int id) {
    SqlRowSet userRow = jdbcTemplate.queryForRowSet("select u.* from FOLLOWER left join PUBLIC.\"user\" u on FOLLOWED_USER_ID = u.USER_ID where FOLLOWING_USER_ID = ?;", id);
    ArrayList<Follower> followers = new ArrayList<>();
    while (userRow.next()) {
      followers.add(new Follower(userRow.getInt("USER_ID")));
    }
    return followers;
  }

  @Override
  public ArrayList<Follower> deleteFriend(int id, int friendId) {
    jdbcTemplate.update(
            "delete from FOLLOWER where FOLLOWING_USER_ID = ? and FOLLOWED_USER_ID = ?;",
            id, friendId);
    return new ArrayList<>();
  }

  @Override
  public ArrayList<Follower> findCommonFriends(int id, int otherId) {
    SqlRowSet userRow = jdbcTemplate.queryForRowSet("select distinct FOLLOWED_USER_ID from FOLLOWER where FOLLOWING_USER_ID in (?,?) and FOLLOWED_USER_ID not in (?,?);",
            id, otherId, id, otherId);
    ArrayList<Follower> followers = new ArrayList<>();
    while (userRow.next()) {
      followers.add(new Follower(userRow.getInt("FOLLOWED_USER_ID")));
    }
    return followers;
  }

  private User buildUser(SqlRowSet userRow) {
    return new User(
            userRow.getInt("USER_ID"),
            userRow.getString("EMAIL"),
            userRow.getString("LOGIN"),
            userRow.getString("NAME"),
            Objects.requireNonNull(userRow.getDate("BIRTHDAY")).toLocalDate()
    );
  }

  private Follower buildFollower(SqlRowSet userRow) {
    return new Follower(userRow.getInt("FOLLOWED_USER_ID")
    );
  }

}