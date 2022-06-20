package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.stream.Stream;

public interface FilmStorage {
    Film add (Film film) throws ValidationException;

    Film update (Film film) throws ValidationException;

    Film delete (Film film);

    Collection<Film> getFilms();

    Stream<Film> getPopularFilms();
}
