package ru.yandex.practicum.filmorate.service.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.BadRequestException;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.interfaces.UserInterface;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserService implements UserInterface {
    private final UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("userDbStorage")UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public User create(User user) {
        checkingUserOnCreate(user);
        log.info("Operation success: Created new user" + user.getLogin());
        return userStorage.create(user);
    }

    @Override
    public User update(User user) {
        checkingUserForUpdate(user);
        log.info("Operation success: User updated " + user.getLogin());
        return userStorage.update(user);
    }

    @Override
    public User delete(User user) {
        checkingUserExist(user.getId());
        log.info("Operation success: User deleted " + user.getLogin());
        return userStorage.delete(user);
    }

    @Override
    public Collection<User> getUsers() {
        return userStorage.getUsers().values();
    }

    @Override
    public User getUserById(int userId) {
        checkingUserExist(userId);
        return userStorage.getUsers().get(userId);
    }

    @Override
    public void addFriend(Integer userId, Integer friendId) {
        checkingUserExist(userId);
        checkingUserExist(friendId);
        checkingId(userId, friendId);
        userStorage.addFriend(userId, friendId);
    }

    @Override
    public List<User> getFriends(Integer userId) {
        checkingUserExist(userId);
        return userStorage.getFriends(userId);
    }

    @Override
    public List<User> getCommonFriendList(Integer userId, Integer otherId) {
        checkingUserExist(userId);
        checkingUserExist(otherId);
        return userStorage.getCommonFriendList(userId, otherId);
    }

    @Override
    public void deleteFriend(Integer userId, Integer friendId) {
        checkingUserExist(userId);
        checkingUserExist(friendId);
        userStorage.deleteFriend(userId, friendId);
    }

    private void checkingUserOnCreate(User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Birthday can't be in future or current date ");
            throw new BadRequestException("Дата рождение не может быть текущей или будущей датой");
        } else if (user.getName().isEmpty() | user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    private void checkingUserForUpdate(User user) {
        if (!userStorage.getUsers().containsKey(user.getId())) {
            log.error("Not found user" + " " + user.getId());
            throw new NotFoundException("Error: can't found user" + user.getId());
        }
    }

    private void checkingUserExist(Integer id) {
        if (!userStorage.getUsers().containsKey(id)) {
            log.error("Can't found user with " + " " + id);
            throw new NotFoundException("Can't found user with " +  id.toString());
        }
    }

    private void checkingId(Integer userId, Integer friendId) {
        if (Objects.equals(userId, friendId)) {
            log.error("You can't be different to yourself");
            throw new BadRequestException("You can't be different to yourself");
        } else if (userId < 1 || friendId < 1) {
            log.error("Id can't be less then 1 ");
            throw new BadRequestException("Some id less than 1" + " " + userId + " or " + friendId);
        }
    }
}
