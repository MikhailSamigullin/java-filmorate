package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Optional;

public interface UserDao {

  Optional<User> findUserById(String id);

  Optional<User> create(User user);

  ArrayList<User> findAll();

  Optional<User> update(User user);

  ArrayList<User> addFriend(int id, int friendId);

  ArrayList<User> findFriends(int id);

  ArrayList<User> deleteFriend(int id, int friendId);

  ArrayList<User> findCommonFriends(int id, int otherId);
}
