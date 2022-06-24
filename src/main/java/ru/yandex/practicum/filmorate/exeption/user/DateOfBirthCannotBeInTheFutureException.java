package ru.yandex.practicum.filmorate.exeption.user;

public class DateOfBirthCannotBeInTheFutureException extends RuntimeException {
    public DateOfBirthCannotBeInTheFutureException(String message){
        super(message);
    }
}
