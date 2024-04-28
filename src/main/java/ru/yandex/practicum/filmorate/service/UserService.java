package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.Follower;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;

import java.util.Optional;

@Service
public class UserService {
  private final UserDao userDao;

  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  public ArrayList<User> findAll() {
    return userDao.findAll();
  }

  public Optional<User> findUserById(String id) {
    return userDao.findUserById(id);
  }

  public ArrayList<Follower> findFriendsByUserId(int id) {
    userDao.findUserById(String.valueOf(id));
    return userDao.findFriends(id);
  }

  public ArrayList<Follower> findCommonFriends(int id, int otherId) {
    userDao.findUserById(String.valueOf(id));
    userDao.findUserById(String.valueOf(otherId));
    return userDao.findCommonFriends(id, otherId);
  }

  public Optional<User> create(User user) {
    return userDao.create(user);
  }

  public Optional<User> update(User user) {
    return userDao.update(user);
  }

  public ArrayList<Follower> addFriend(int id, int friendId) {
    userDao.findUserById(String.valueOf(id));
    userDao.findUserById(String.valueOf(friendId));
    return userDao.addFriend(id, friendId);
  }

  public ArrayList<Follower> deleteFriend(int id, int friendId) {
    userDao.findUserById(String.valueOf(id));
    userDao.findUserById(String.valueOf(friendId));
    return userDao.deleteFriend(id, friendId);
  }

}
