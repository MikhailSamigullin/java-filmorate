package ru.yandex.practicum.filmorate.storage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserDbStorageTest {
  private final UserDao userStorage;

  @Autowired
  UserDbStorageTest(UserDao userStorage) {
    this.userStorage = userStorage;
  }

  @Test
  public void testFindUserById() {
    User user = new User(1, "user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
    Optional<User> user1 = userStorage.create(user);
    Optional<User> user2 = userStorage.findUserById(String.valueOf(user.getId()));
    if (user1.isPresent() && user2.isPresent()) {
      assertEquals(user1.get(), user2.get());
    }
  }

  @Test
  public void testFindAll() {
    User user1 = new User(1, "user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
    User user2 = new User(2, "user@email.ru", "vanya1234", "Ivan Petrov", LocalDate.of(1990, 1, 1));
    ArrayList<User> users = new ArrayList<>();
    users.add(user1);
    users.add(user2);
    userStorage.create(user1);
    userStorage.create(user2);
    ArrayList<User> createdUsers = userStorage.findAll();
    assertEquals(users.size(), createdUsers.size());
  }

  @Test
  public void testCreate() {
    User user = new User(1, "user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
    Optional<User> user1 = userStorage.create(user);
    Optional<User> user2 = userStorage.findUserById(String.valueOf(user.getId()));
    if (user1.isPresent() && user2.isPresent()) {
      assertEquals(user1.get(), user2.get());
    }
  }

  @Test
  public void testUpdate() {
    User user = new User(1, "user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
    Optional<User> user1 = userStorage.create(user);
    Optional<User> user2 = userStorage.findUserById(String.valueOf(user.getId()));
    if (user1.isPresent() && user2.isPresent()) {
      assertEquals(user1.get(), user2.get());
    }
    User newUser = new User(1, "user1@email.ru", "vanya1235", "Ivan Petrov1", LocalDate.of(1990, 1, 1));
    Optional<User> user3 = userStorage.update(newUser);
    Optional<User> user4 = userStorage.findUserById(String.valueOf(newUser.getId()));
    if (user3.isPresent() && user4.isPresent()) {
      assertEquals(user3.get(), user4.get());
    }
  }
}
