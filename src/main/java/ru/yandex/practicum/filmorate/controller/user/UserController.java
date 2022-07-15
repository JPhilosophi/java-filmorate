package ru.yandex.practicum.filmorate.controller.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.interfaces.UserInterface;
import ru.yandex.practicum.filmorate.service.services.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@Validated
@RequestMapping("/users")
public class UserController {
    private final UserInterface userInterface;

    @Autowired
    public UserController(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    @GetMapping
    public Collection<User> getUsers() {
        return userInterface.getUsers();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable int userId) {
        return userInterface.getUserById(userId);
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        userInterface.create(user);
        return user;
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) {
        userInterface.update(user);
        return user;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        userInterface.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        userInterface.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable Integer id) {
        return userInterface.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriend(@PathVariable Integer id, @PathVariable Integer otherId) {
        return userInterface.getCommonFriendList(id, otherId);
    }

    @DeleteMapping
    public User deleteUser(@Valid User user) { return userInterface.delete(user); }
}