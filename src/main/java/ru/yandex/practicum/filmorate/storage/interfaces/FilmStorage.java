package ru.yandex.practicum.filmorate.storage.interfaces;

import org.springframework.lang.Nullable;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Map;
import java.util.Set;

public interface FilmStorage {
    Film save (Film film);

    Film update (Film film);

    Film delete (Film film);

    Map<Integer, Film> getFilms(@Nullable Integer count);

    Map<Integer, Set<Integer>> getLikes();

    void addLike(Integer filmId, Integer userId);

    void deleteLike(Integer filmId, Integer userId);
}
