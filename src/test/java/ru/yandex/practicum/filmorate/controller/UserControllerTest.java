package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class UserControllerTest {
    private User user = new User();
    private UserController userController = new UserController();


    @Test
    public void successCreateUser() throws ValidationException {
        user.setId(1);
        user.setName("Name");
        user.setLogin("Login");
        user.setBirthday(LocalDate.of(1900, 12, 30));
        user.setEmail("mail.mail@com");
        assertNotNull(userController.create(user));
    }

    @Test
    public void successUpdateUser() throws ValidationException {
        user.setId(1);
        user.setName("Name");
        user.setLogin("Login");
        user.setBirthday(LocalDate.of(1900, 12, 30));
        user.setEmail("mail.mail@com");
        assertNotNull(userController.create(user));
        user.setId(1);
        user.setName("Name2");
        user.setLogin("Login2");
        user.setBirthday(LocalDate.of(1900, 12, 30));
        user.setEmail("mail2.mail@com");
        assertNotNull(userController.put(user));
    }

    @Test
    public void shouldReturnErrorByBirthday() {
        user.setId(1);
        user.setName("Name");
        user.setLogin("Login");
        user.setBirthday(LocalDate.now());
        user.setEmail("mail.mail@com");
        assertThrows(ValidationException.class, () -> userController.create(user));
    }

    @Test
    public void shouldReturnErrorNotFound() {
        user.setName("Name");
        user.setLogin("Login");
        user.setBirthday(LocalDate.now());
        user.setEmail("mail.mail@com");
        assertThrows(ValidationException.class, () -> userController.put(user));
    }
}

