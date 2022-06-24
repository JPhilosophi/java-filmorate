package ru.yandex.practicum.filmorate.exeption.friend;

public class IdLessThanOneException extends RuntimeException {
    public IdLessThanOneException(String message) {
        super(message);
    }
}

