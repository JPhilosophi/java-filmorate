package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.film.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    private final InMemoryFilmStorage filmStorage;
    private final LocalDate TIME = LocalDate.of(1895, 12, 28);

    @Autowired
    public FilmService(InMemoryFilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film save(Film film) {
        checkingFilmOnCreate(film);
        log.info("Operation success: Saved create new object" + film.getName());
        return filmStorage.save(film);
    }

    public Film update(Film film) {
        checkingFilmOnUpdate(film);
        log.info("Operation success: Movie is delete" + film.getName());
        return filmStorage.update(film);
    }

    public Film delete(Film film) {
        checkingFilmOnUpdate(film);
        return filmStorage.delete(film);
    }

    public Collection<Film> get() {
        return filmStorage.getFilms().values();
    }

    public List<Film> getPopular(Integer count) {
        if (count == null) {
            return filmStorage.getFilms().values()
                    .stream()
                    .sorted(Comparator.comparing(Film::getRate))
                    .collect(Collectors.toList());
        }
        Map<Integer, Set<Integer>> result = new TreeMap<Integer, Set<Integer>>(filmStorage.getLikes());
        return result.keySet().stream()
                .map(filmStorage.getFilms()::get)
                .limit(Objects.requireNonNullElse(count, 10))
                .collect(Collectors.toList());
    }

    public Film getById(Integer filmId) {
        checkingAvailabilityFilm(filmId);
        return filmStorage.getFilms().get(filmId);
    }

    public void addLike(Integer filmId, Integer userId) {
        filmStorage.addLike(filmId, userId);
    }

    public void deleteLike(Integer filmId, Integer userId) {
        filmStorage.deleteLike(filmId, userId);
    }

    private void checkingFilmOnCreate(Film film) {
        if (film.getDescription().length() > 200) {
            throw new FilmDescriptionException("Movie description cannot exceed 200 characters");
        } else if (film.getReleaseDate().isBefore(TIME)) {
            throw new FilmDateException("movies date cannot be earlier" + TIME);
        } else if (film.getDuration().isNegative()) {
            throw new FilmDurationException("movie duration cannot be negative or equal to 0");
        }
    }

    private void checkingFilmOnUpdate(Film film) {
        if (!filmStorage.getFilms().containsKey(film.getId())) {
            throw new FilmDoesntExistException(" movie with " + film.getId() + " is not in the database");
        }
    }

    private void checkingAvailabilityFilm(Integer filmId) {
        if (!filmStorage.getFilms().containsKey(filmId)) {
            throw new FilmIncorrectId( " movie with " + filmId + " is not in the database");
        }
    }

}
