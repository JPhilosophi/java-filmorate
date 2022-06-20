package ru.yandex.practicum.filmorate.exeption.user;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String message) {
        super(message);
    }
}
