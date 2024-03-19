package ru.yandex.practicum.filmorate.util;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilTest {

  @Test
  void findMaxId() {
    int expectedMaxId = 1;
    User user1 = new User(0, "mail@mail.ru", "dolore", "Nick Name", LocalDate.of(1946, 8, 20));
    User user2 = new User(1, "mail@mail.ru", "dolore", "Nick Name", LocalDate.of(1946, 8, 20));
    HashMap<Integer, User> users = new HashMap<>();
    users.put(0, user1);
    users.put(1, user2);
    assertEquals(expectedMaxId, Util.findMaxId(users.keySet()));
  }

  @Test
  void findMaxIdInEmptySet() {
    int expectedMaxId = 0;
    HashMap<Integer, User> users = new HashMap<>();
    assertEquals(expectedMaxId, Util.findMaxId(users.keySet()));
  }

}