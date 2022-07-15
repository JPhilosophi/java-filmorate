package ru.yandex.practicum.filmorate.service.interfaces;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface IFilmService {

    Film save(Film film);

    Film update(Film film);

    Film delete(Film film);

    Collection<Film> get();

    List<Film> getPopular(Integer count);

    Film getById(Integer filmId);

    void addLike(Integer filmId, Integer userId);

    void deleteLike(Integer filmId, Integer userId);
}
