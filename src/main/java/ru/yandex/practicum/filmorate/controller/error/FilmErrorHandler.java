package ru.yandex.practicum.filmorate.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exeption.film.*;
import ru.yandex.practicum.filmorate.model.ErrorResponse;

@RestControllerAdvice
public class FilmErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDescriptionException(final FilmDescriptionException e) {
        return new ErrorResponse("Error: description cannot exceed 200 characters  " + e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDateException(final FilmDateException e) {
        return new ErrorResponse("Error: date cannot be earlier 1895-12-28  " + e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDurationException(final FilmDurationException e) {
        return new ErrorResponse("Error: duration cannot be negative or equal to 0  " + e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse  handleIncorrectFilmUpdateException(final FilmDoesntExistException e) {
        return new ErrorResponse("Error: duration cannot be negative or equal to 0  " + e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse  handleIncorrectFilmId(final FilmIncorrectId e) {
        return new ErrorResponse("Error: incorrect film id  " + e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse  handleDoesntHaveLikes(final FilmDoesntHaveEnyLikes e) {
        return new ErrorResponse("Error: films doesn't have any likes  " + e.getMessage());
    }
}
