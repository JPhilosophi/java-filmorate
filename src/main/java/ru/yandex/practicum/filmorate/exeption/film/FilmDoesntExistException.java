package ru.yandex.practicum.filmorate.exeption.film;

public class FilmDoesntExistException extends RuntimeException {
    public FilmDoesntExistException(String message) {
        super(message);
    }
}
