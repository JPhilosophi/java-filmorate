package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {

    User add (User user) throws ValidationException;

    User update(User user) throws ValidationException;

    User delete (User user);

    Collection<User> getUsers();

    User getUserById(int userId);

    void addFriend(Integer id, Integer friendId);
}
