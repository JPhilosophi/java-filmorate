package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exeption.BadRequestException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.memory_storage.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class UserControllerTest {
    private final User user = new User();
    private final InMemoryUserStorage userStorage;

    public UserControllerTest(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }


    @Test
    public void successCreateUser() {
        user.setId(1);
        user.setName("Name");
        user.setLogin("Login");
        user.setBirthday(LocalDate.of(1900, 12, 30));
        user.setEmail("mail.mail@com");
        assertNotNull(userStorage.create(user));
    }

    @Test
    public void successUpdateUser() {
        user.setId(1);
        user.setName("Name");
        user.setLogin("Login");
        user.setBirthday(LocalDate.of(1900, 12, 30));
        user.setEmail("mail.mail@com");
        assertNotNull(userStorage.create(user));
        user.setId(1);
        user.setName("Name2");
        user.setLogin("Login2");
        user.setBirthday(LocalDate.of(1900, 12, 30));
        user.setEmail("mail2.mail@com");
        assertNotNull(userStorage.update(user));
    }

    @Test
    public void shouldReturnErrorByBirthday() {
        user.setId(1);
        user.setName("Name");
        user.setLogin("Login");
        user.setBirthday(LocalDate.now());
        user.setEmail("mail.mail@com");
        assertThrows(BadRequestException.class, () -> userStorage.create(user));
    }

    @Test
    public void shouldReturnErrorNotFound() {
        user.setName("Name");
        user.setLogin("Login");
        user.setBirthday(LocalDate.now());
        user.setEmail("mail.mail@com");
        assertThrows(BadRequestException.class, () -> userStorage.update(user));
    }
}

