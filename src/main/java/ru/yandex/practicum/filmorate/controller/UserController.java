package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final List<User> users = new ArrayList<>();
    private static final  Pattern LOGIN_PATTERN = Pattern.compile("^[A-Za-z]([\\\\.A-Za-z0-9-]{1,18})([A-Za-z0-9])$");
    private Matcher loginMatcher;

    @GetMapping
    public List<User> getUsers() {
        return users;
    }

    @PostMapping
    public User create(@RequestBody User user) throws ValidationException {
        loginMatcher = LOGIN_PATTERN.matcher(user.getLogin());
        boolean loginMatches = loginMatcher.matches();
        if (!EmailValidator.getInstance().isValid(user.getEmail())) {
            throw new ValidationException("Адрес электронной имеет не верный формат");
        } else if (!loginMatches) {
            throw new ValidationException("Логин не может быть пустым или содержать пробелы");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождение не может быть текущей или будущей датой");
        } else {
            users.add(user);
        }
        return user;
    }

    @PutMapping
    public User put(@RequestBody User user) throws ValidationException {
        loginMatcher = LOGIN_PATTERN.matcher(user.getLogin());
        boolean loginMatches = loginMatcher.matches();
        if (!EmailValidator.getInstance().isValid(user.getEmail())) {
            throw new ValidationException("Адрес электронной имеет не верный формат");
        } else if (!loginMatches) {
            throw new ValidationException("Логин не может быть пустым или содержать пробелы");
        } else if (users.contains(user.getLogin())) {
            throw new ValidationException("Такой логин уже есть");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождение не может быть текущей или будущей датой");
        } else {
            users.add(user);
        }
        return user;
    }
}
