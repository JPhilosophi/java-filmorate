package ru.yandex.practicum.filmorate.storage.memory_storage;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Component("inMemoryFilmStorage")
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private final Map<Integer, Set<Integer>> likes = new HashMap<>();
    private static int filmId = 1;

    @Override
    public Film save(Film film) {
        film.setId(getNextId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film delete(Film film) {
        films.remove(film.getId());
        return film;
    }

    @Override
    public Map<Integer, Film> getFilms(@Nullable Integer count) {
        return films;
    }

    @Override
    public Map<Integer, Set<Integer>> getLikes() {
        return likes;
    }

    @Override
    public void addLike(Integer filmId, Integer userId) {
        if (!likes.containsKey(filmId)) {
           likes.put(filmId, new HashSet<>());
       } likes.get(filmId).add(userId);
    }

    @Override
    public void deleteLike(Integer filmId, Integer userId) {
        likes.get(filmId).remove(userId);
    }

    public int getNextId() {
        return filmId++;
    }
}


