package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;

@Service
public class UserService {
  private final UserStorage userStorage;

  public UserService(UserStorage userStorage) {
    this.userStorage = userStorage;
  }

  public ArrayList<User> findAll() {
    return userStorage.findAll();
  }

  public User findById(int id) {
    userStorage.checkUserId(id);
    return userStorage.findById(id);
  }

  public ArrayList<User> findFriendsByUserId(int id) {
    userStorage.checkUserId(id);
    return userStorage.findFriends(id);

  }

  public ArrayList<User> findCommonFriends(int id, int otherId) {
    userStorage.checkUserId(id);
    userStorage.checkUserId(otherId);
    return userStorage.findCommonFriends(id, otherId);
  }

  public User create(User user) {
    return userStorage.create(user);
  }

  public User update(User user) {
    userStorage.checkUserId(user.getId());
    return userStorage.update(user);
  }

  public User addFriend(int id, int friendId) {
    userStorage.checkUserId(id);
    userStorage.checkUserId(friendId);
    return userStorage.addFriend(id, friendId);
  }

  public User deleteFriend(int id, int friendId) {
    userStorage.checkUserId(id);
    userStorage.checkUserId(friendId);
    return userStorage.deleteFriend(id, friendId);
  }

}
