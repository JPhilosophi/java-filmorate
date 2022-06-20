package ru.yandex.practicum.filmorate.exeption.user;

public class LoginCantBeNullExceprion extends RuntimeException {
    public LoginCantBeNullExceprion(String message) {
        super(message);
    }
}
