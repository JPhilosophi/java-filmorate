package ru.yandex.practicum.filmorate.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) throws ValidationException {
        log.info("Запрос получен.");
        if (!EmailValidator.getInstance().isValid(user.getEmail())) {
            throw new ValidationException("Адрес электронной имеет не верный формат");
        } else if (user.getLogin().isEmpty() | user.getLogin().isBlank()) {
            throw new ValidationException("Логин не может быть пустым или содержать пробелы");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождение не может быть текущей или будущей датой");
        } else if (user.getName().isEmpty() | user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(users.size() + 1);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User put(@RequestBody User user) throws ValidationException {
        log.info("Запрос получен.");
        if (!EmailValidator.getInstance().isValid(user.getEmail())) {
            throw new ValidationException("Адрес электронной имеет не верный формат");
        } else if (user.getLogin().isEmpty() | user.getLogin().isBlank()) {
            throw new ValidationException("Логин не может быть пустым или содержать пробелы");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождение не может быть текущей или будущей датой");
        } else if (user.getName().isEmpty() | user.getName().isBlank()) {
            user.setName(user.getLogin());
        } else if (!users.containsKey(user.getId())) {
            throw new ValidationException("Дата рождение не может быть текущей или будущей датой");
        }
        users.put(user.getId(), user);
        return user;
    }
}
