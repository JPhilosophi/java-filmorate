package ru.yandex.practicum.filmorate.controller.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@RestController
@Validated
@RequestMapping("/users")
public class UserController {
    private final InMemoryUserStorage userStorage;

    @Autowired
    public UserController(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @GetMapping
    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable int userId) {
        return userStorage.getUserById(userId);
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        userStorage.add(user);
        return user;
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) {
        userStorage.update(user);
        return user;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        userStorage.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        userStorage.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable Integer id) {
        return userStorage.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriend(@PathVariable Integer id, @PathVariable Integer otherId) {
        return userStorage.getCommonFriendList(id, otherId);
    }
}