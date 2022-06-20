package ru.yandex.practicum.filmorate.exeption.user;

public class UserDoesntExistException extends RuntimeException{
    public UserDoesntExistException(String message) {
        super(message);
    }
}
