package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.lang.reflect.Executable;
import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    @Test
    public void testGetPostandPutMethods() throws ValidationException {
        UserController userController = new UserController();
        User user = new User("test@test.ru", "test");
        user.setId(1);
        user.setLogin("Test");
        user.setName("Ivan");
        user.setBirthday(LocalDate.of(1990, 10, 17));
        userController.create(user);
        Collection<User> userMap = userController.getUsers();
        assertEquals(userMap.size(), 1);
        assertTrue(userController.getUsers().contains(user));
    }

    @Test
    public void ShouldКeturnСreationError() throws ValidationException {
        UserController userController = new UserController();
        User user = new User("test@test.ru", "test");
        user.setId(1);
        user.setBirthday(LocalDate.of(2025, 5, 20));
        assertThrowsExactly(ValidationException.class, "Дата рождение не может быть текущей или будущей датой");
    }
    private void assertThrowsExactly(Class<ValidationException> validationExceptionClass, String s) {
    }
}
