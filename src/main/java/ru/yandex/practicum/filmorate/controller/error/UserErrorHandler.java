package ru.yandex.practicum.filmorate.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exeption.friend.IdLessThanOneException;
import ru.yandex.practicum.filmorate.exeption.user.DateOfBirthCannotBeInTheFutureException;
import ru.yandex.practicum.filmorate.exeption.user.InvalidEmailException;
import ru.yandex.practicum.filmorate.exeption.user.LoginCantBeNullExceprion;
import ru.yandex.practicum.filmorate.exeption.user.UserDoesntExistException;
import ru.yandex.practicum.filmorate.model.ErrorResponse;

@RestControllerAdvice
public class UserErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIncorrectEmailException(final InvalidEmailException e) {
        return new ErrorResponse("Error: email incorrect " + e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIncorrectBirthDayException(final DateOfBirthCannotBeInTheFutureException e) {
        return new ErrorResponse("Error: birthday incorrect " + e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIncorrectLoginException(final LoginCantBeNullExceprion e) {
        return new ErrorResponse("Error: login incorrect " + e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleIncorrectUserUpdateException(final UserDoesntExistException e) {
        return new ErrorResponse("Error: user can't find " + e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleIncorrectIdException(final IdLessThanOneException e) {
        return new ErrorResponse("Error: id less than 1 " + e.getMessage());
    }
}
