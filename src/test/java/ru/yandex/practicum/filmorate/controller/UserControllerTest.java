package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserControllerTest {

    @Test
    public void testGetPostandPutMethods() throws ValidationException {
        UserController userController = new UserController();
        User user = new User();
        user.setId(1);
        user.setLogin("Test");
        user.setName("Ivan");
        user.setEmail("test@test.ru");
        user.setBirthday(LocalDate.of(1990, 10, 17));
        userController.create(user);
        Collection<User> userMap = userController.getUsers();
        assertEquals(userMap.size(), 1);
        assertTrue(userController.getUsers().contains(user));
    }

}
