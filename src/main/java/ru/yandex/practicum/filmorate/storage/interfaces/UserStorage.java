package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;

public interface UserStorage {

    User create(User user);

    User update(User user);

    User delete (User user);

    Map<Integer, User> getUsers();

    void addFriend(Integer id, Integer friendId);

    List<User> getFriends(Integer userId);

    List<User> getCommonFriendList(Integer userId, Integer otherId);

    void deleteFriend(Integer userId, Integer friendId);
}
