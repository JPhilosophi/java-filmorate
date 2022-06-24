package ru.yandex.practicum.filmorate.exeption.film;

public class FilmDoesntHaveEnyLikes extends RuntimeException{
    public FilmDoesntHaveEnyLikes(String message) {
        super(message);
    }
}
