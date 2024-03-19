package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.Util;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("users")
public class UserController {
  private final HashMap<Integer, User> users = new HashMap<>();

  @GetMapping
  public ArrayList<User> findAll() {
    return new ArrayList<>(users.values());
  }

  @PostMapping
  public User create(@Valid @RequestBody User user) {
    int id = Util.findMaxId(users.keySet()) + 1;
    if (user.getName() == null || user.getName().isEmpty()) {
      user.setName(user.getLogin());
    }
    user.setId(id);
    users.put(id, user);
    return user;
  }

  @PutMapping
  public User update(@Valid @RequestBody User user) {
    if (users.containsKey(user.getId())) {
      users.put(user.getId(), user);
    } else {
      throw new RuntimeException("Такого id не существует.");
    }
    return user;
  }

}
