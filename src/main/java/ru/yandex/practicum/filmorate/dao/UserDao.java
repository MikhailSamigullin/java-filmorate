package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Follower;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Optional;

public interface UserDao {

  Optional<User> findUserById(String id);

  Optional<User> create(User user);

  ArrayList<User> findAll();

  Optional<User> update(User user);

  ArrayList<Follower> addFriend(int id, int friendId);

  ArrayList<Follower> findFriends(int id);

  ArrayList<Follower> deleteFriend(int id, int friendId);

  ArrayList<Follower> findCommonFriends(int id, int otherId);
}
