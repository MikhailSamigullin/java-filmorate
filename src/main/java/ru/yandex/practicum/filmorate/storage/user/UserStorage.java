package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;

public interface UserStorage {
  ArrayList<User> findAll();

  User findById(int id);

  ArrayList<User> findFriends(int id);

  ArrayList<User> findCommonFriends(int id, int otherId);

  User create(User user);

  User update(User user);

  User addFriend(int id, int friendId);

  User deleteFriend(int id, int friendId);

  void checkUserId(int id);
}
