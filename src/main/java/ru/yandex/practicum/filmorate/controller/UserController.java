package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("users")
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ArrayList<User> findAll() {
    return userService.findAll();
  }

  @GetMapping("{id}")
  public Optional<User> findById(@PathVariable String id) {
    return userService.findUserById(id);
  }

  @GetMapping("/{id}/friends")
  public ArrayList<User> findFriendsByUserId(@PathVariable int id) {
    return userService.findFriendsByUserId(id);
  }

  @GetMapping("/{id}/friends/common/{otherId}")
  public ArrayList<User> findCommonFriends(@PathVariable int id, @PathVariable int otherId) {
    return userService.findCommonFriends(id, otherId);
  }

  @PostMapping
  public Optional<User> create(@Valid @RequestBody User user) {
    return userService.create(user);
  }

  @PutMapping
  public Optional<User> update(@Valid @RequestBody User user) {
    return userService.update(user);
  }

  @PutMapping("/{id}/friends/{friendId}")
  public ArrayList<User> addFriend(@PathVariable int id, @PathVariable  int friendId) {
    return userService.addFriend(id, friendId);
  }

  @DeleteMapping("/{id}/friends/{friendId}")
  public ArrayList<User> deleteFriend(@PathVariable int id, @PathVariable int friendId) {
    return userService.deleteFriend(id, friendId);
  }

}
