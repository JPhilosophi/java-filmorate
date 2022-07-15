package ru.yandex.practicum.filmorate.service.interfaces;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

public interface UserInterface {

    User create(User user);

    User update(User user);

    User delete(User user);

    Collection<User> getUsers();

    User getUserById(int userId);

    void addFriend(Integer userId, Integer friendId);

    List<User> getFriends(Integer userId);

    List<User> getCommonFriendList(Integer userId, Integer otherId);

    void deleteFriend(Integer userId, Integer friendId);


}
