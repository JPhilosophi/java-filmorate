package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        List<User> userList = userController.getUsers();
        assertEquals(userList.size(), 1);
        assertEquals(user, userController.getUsers().get(0));
    }

}
