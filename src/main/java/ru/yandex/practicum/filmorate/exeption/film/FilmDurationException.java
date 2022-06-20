package ru.yandex.practicum.filmorate.exeption.film;

public class FilmDurationException extends RuntimeException {
    public FilmDurationException(String message) {
        super(message);
    }
}
