package ru.yandex.practicum.filmorate.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exeption.user.InvalidEmailException;
import ru.yandex.practicum.filmorate.model.ErrorResponse;

@RestControllerAdvice
public class FriendErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConflictFriendIdException(final InvalidEmailException e) {
        return new ErrorResponse("Error: email incorrect " + e.getMessage());
    }
}
