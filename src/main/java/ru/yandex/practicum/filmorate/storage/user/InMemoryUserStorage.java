package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotExistsException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

@Component
public class InMemoryUserStorage implements UserStorage {
  HashMap<Integer, User> users = new HashMap<>();

  @Override
  public ArrayList<User> findAll() {
    return new ArrayList<>(users.values());
  }

  @Override
    public Optional<User> findById(int id) {
    checkUserId(id);
    return Optional.ofNullable(users.get(id));
  }

  @Override
  public ArrayList<User> findFriends(int id) {
    ArrayList<User> friendList = new ArrayList<>();
    Set<Integer> friends = users.get(id).getFriends();
    friends.forEach(friendId -> friendList.add(users.get(friendId)));
    return friendList;
  }

  @Override
  public ArrayList<User> findCommonFriends(int id, int otherId) {
    Set<Integer> firstUserFriends = users.get(id).getFriends();
    Set<Integer> secondUserFriends = users.get(otherId).getFriends();
    ArrayList<User> commonFriends = new ArrayList<>();
    firstUserFriends.stream()
            .filter(secondUserFriends::contains)
            .forEach(friendsId -> commonFriends.add(users.get(friendsId)));
    return commonFriends;
  }

  @Override
  public User create(User user) {
    int id = Util.findMaxId(users.keySet()) + 1;
    if (user.getName() == null || user.getName().isEmpty()) {
      user.setName(user.getLogin());
    }
    user.setId(id);
    users.put(id, user);
    return user;
  }

  @Override
  public User update(User user) {
    if (user.getName() == null || user.getName().isEmpty()) {
      user.setName(user.getLogin());
    }
    users.put(user.getId(), user);
    return user;
  }

  @Override
  public User addFriend(int id, int friendId) {
    checkUserId(id);
    checkUserId(friendId);
    users.get(id).getFriends().add(friendId);
    users.get(friendId).getFriends().add(id);
    return users.get(id);
  }

  @Override
  public User deleteFriend(int id, int friendId) {
    User user = users.get(id);
    User friend = users.get(friendId);
    user.getFriends().remove(friendId);
    friend.getFriends().remove(id);
    return user;
  }

  @Override
  public void checkUserId(int id) {
    if (!users.containsKey(id)) {
      throw new NotExistsException("Такого id пользователя не существует.");
    }
  }
}
