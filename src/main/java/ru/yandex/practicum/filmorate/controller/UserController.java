package ru.yandex.practicum.filmorate.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@Validated
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) throws ValidationException {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Birthday can't be in future or current date ");
            throw new ValidationException("Дата рождение не может быть текущей или будущей датой");
        } else if (user.getName().isEmpty() | user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(users.size() + 1);
        users.put(user.getId(), user);
        log.info("Operation success: Created new user" + user.getLogin());
        return user;
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) throws ValidationException {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Birthday can't be in future or current date ");
            throw new ValidationException("Дата рождение не может быть текущей или будущей датой");
        } else if (user.getName().isEmpty() | user.getName().isBlank()) {
            user.setName(user.getLogin());
        } else if (!users.containsKey(user.getId())) {
            log.error("User with " + " " + user.getId() + " not in the system ");
            throw new ValidationException("Пользователь с " + user.getId() + " уже есть в системе");
        }
        users.put(user.getId(), user);
        log.info("Operation success: User updated " + user.getLogin());
        return user;
    }
}
