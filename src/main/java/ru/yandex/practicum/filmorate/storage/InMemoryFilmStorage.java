package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.film.FilmDoesntHaveEnyLikes;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Component
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
    public Map<Integer, Film> getFilms() {
        return films;
    }

    @Override
    public Map<Integer, Set<Integer>> getLikes() {
        return likes;
    }

    public void addLike(Integer filmId, Integer userId) {
        if (!likes.containsKey(filmId)) {
           likes.put(filmId, new HashSet<>());
       } likes.get(filmId).add(userId);
    }

    public void deleteLike(Integer filmId, Integer userId) {
        checkLikes(filmId);
        likes.get(filmId).remove(userId);
    }

    public void checkLikes(Integer id) {
        if(!likes.containsKey(id)){
            throw new FilmDoesntHaveEnyLikes("Films doesn't have any likes");
        }
    }

    public int getNextId() {
        return filmId++;
    }
}


