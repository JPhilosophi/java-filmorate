package ru.yandex.practicum.filmorate.controller.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exeption.film.*;
import ru.yandex.practicum.filmorate.model.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class FilmErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDescriptionException(final FilmDescriptionException e) {
        log.error("Movie description cannot exceed 200 characters");
        return new ErrorResponse("Error: description cannot exceed 200 characters  " + e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDateException(final FilmDateException e) {
        log.error("Error: movies date cannot be earlier");
        return new ErrorResponse("Error: date cannot be earlier 1895-12-28  " + e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDurationException(final FilmDurationException e) {
        log.error("Error: movie duration cannot be negative or equal to 0");
        return new ErrorResponse("Error: duration cannot be negative or equal to 0  " + e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse  handleIncorrectFilmUpdateException(final FilmDoesntExistException e) {
        log.error("Error: movie with  is not in the database");
        return new ErrorResponse("Error: duration cannot be negative or equal to 0  " + e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse  handleIncorrectFilmId(final FilmIncorrectId e) {
        log.error("Can't found film");
        return new ErrorResponse("Error: incorrect film id  " + e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse  handleDoesntHaveLikes(final FilmDoesntHaveEnyLikes e) {
        return new ErrorResponse("Error: films doesn't have any likes  " + e.getMessage());
    }
}
