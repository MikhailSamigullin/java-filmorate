package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotExistsException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.util.Util;

import java.util.ArrayList;
import java.util.Set;

@Service
public class UserService {
  private final InMemoryUserStorage inMemoryUserStorage;

  public UserService(InMemoryUserStorage inMemoryUserStorage) {
    this.inMemoryUserStorage = inMemoryUserStorage;
  }

  public ArrayList<User> findAll() {
    return new ArrayList<>(inMemoryUserStorage.users.values());
  }

  public User findById(int id) {
    checkUserId(id);
    return inMemoryUserStorage.users.get(id);
  }

  public ArrayList<User> findFriendsByUserId(int id) {
    checkUserId(id);
    return findFriends(id);

  }

  public ArrayList<User> findCommonFriends(int id, int otherId) {
    checkUserId(id, otherId);
    Set<Integer> firstUserFriends = inMemoryUserStorage.users.get(id).getFriends();
    Set<Integer> secondUserFriends = inMemoryUserStorage.users.get(otherId).getFriends();
    ArrayList<User> commonFriends = new ArrayList<>();
    firstUserFriends.stream()
            .filter(secondUserFriends::contains)
            .forEach(friendsId -> commonFriends.add(inMemoryUserStorage.users.get(friendsId)));
    return commonFriends;
  }

  public User create(User user) {
    int id = Util.findMaxId(inMemoryUserStorage.users.keySet()) + 1;
    if (user.getName() == null || user.getName().isEmpty()) {
      user.setName(user.getLogin());
    }
    user.setId(id);
    inMemoryUserStorage.users.put(id, user);
    return user;
  }

  public User update(User user) {
    checkUserId(user.getId());
    inMemoryUserStorage.users.put(user.getId(), user);
    return user;
  }

  public User addFriend(int id, int friendId) {
    checkUserId(id, friendId);
    inMemoryUserStorage.users.get(id).getFriends().add(friendId);
    inMemoryUserStorage.users.get(friendId).getFriends().add(id);
    return inMemoryUserStorage.users.get(id);
  }

  public User deleteFriend(int id, int friendId) {
    checkUserId(id, friendId);
    User user = inMemoryUserStorage.users.get(id);
    User friend = inMemoryUserStorage.users.get(friendId);
    user.getFriends().remove(friendId);
    friend.getFriends().remove(id);
    return user;
  }

  private ArrayList<User> findFriends(int id) {
    ArrayList<User> friendList = new ArrayList<>();
    Set<Integer> friends = inMemoryUserStorage.users.get(id).getFriends();
    friends.forEach(friendId -> friendList.add(inMemoryUserStorage.users.get(friendId)));
    return friendList;
  }

  private void checkUserId(int id) {
    if (!inMemoryUserStorage.users.containsKey(id)) {
      throw new NotExistsException("Такого id не существует.");
    }
  }

  private void checkUserId(int id, int otherId) {
    if (!inMemoryUserStorage.users.containsKey(id) || !inMemoryUserStorage.users.containsKey(otherId)) {
      throw new RuntimeException("Такого id не существует.");
    }
  }
}
