package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.BadRequestException;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserService {
    private final InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public User create(User user) {
        checkingUserOnCreate(user);
        log.info("Operation success: Created new user" + user.getLogin());
        return inMemoryUserStorage.create(user);
    }

    public User update(User user) {
        checkingUserForUpdate(user);
        log.info("Operation success: User updated " + user.getLogin());
        return inMemoryUserStorage.update(user);
    }

    public User delete(User user) {
        checkingUserExist(user.getId());
        log.info("Operation success: User deleted " + user.getLogin());
        return inMemoryUserStorage.delete(user);
    }

    public Collection<User> getUsers() {
        return inMemoryUserStorage.getUsers().values();
    }

    public User getUserById(int userId) {
        checkingUserExist(userId);
        return inMemoryUserStorage.getUsers().get(userId);
    }

    public void addFriend(Integer userId, Integer friendId) {
        checkingUserExist(userId);
        checkingUserExist(friendId);
        checkingId(userId, friendId);
        inMemoryUserStorage.addFriend(userId, friendId);
    }

    public List<User> getFriends(Integer userId) {
        checkingUserExist(userId);
        return inMemoryUserStorage.getFriends(userId);
    }

    public List<User> getCommonFriendList(Integer userId, Integer otherId) {
        checkingUserExist(userId);
        checkingUserExist(otherId);
        return inMemoryUserStorage.getCommonFriendList(userId, otherId);
    }

    public void deleteFriend(Integer userId, Integer friendId) {
        checkingUserExist(userId);
        checkingUserExist(friendId);
        inMemoryUserStorage.deleteFriend(userId, friendId);
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
        if (!inMemoryUserStorage.getUsers().containsKey(user.getId())) {
            log.error("Not found user" + " " + user.getId());
            throw new NotFoundException("Error: can't found user" + user.getId());
        }
    }

    private void checkingUserExist(Integer id) {
        if (!inMemoryUserStorage.getUsers().containsKey(id)) {
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
