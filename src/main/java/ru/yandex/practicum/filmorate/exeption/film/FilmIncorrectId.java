package ru.yandex.practicum.filmorate.exeption.film;

public class FilmIncorrectId extends RuntimeException{
    public FilmIncorrectId(String message) {
        super(message);
    }
}
