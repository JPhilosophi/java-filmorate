package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.film.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final LocalDate TIME = LocalDate.of(1895, 12, 28);
    private final Map<Integer, Film> films = new HashMap<>();
    private final Map<Integer, Set<Integer>> likes = new HashMap<>();

    @Override
    public Film add(Film film) {
        if (film.getDescription().length() > 200) {
            log.error("Movie description cannot exceed 200 characters");
            throw new FilmDescriptionException("Movie description cannot exceed 200 characters");
        } else if (film.getReleaseDate().isBefore(TIME)) {
            log.error("Error: movies date cannot be earlier" + TIME);
            throw new FilmDateException("movies date cannot be earlier" + TIME);
        } else if (film.getDuration().isNegative()) {
            log.error("Error: movie duration cannot be negative or equal to 0");
            throw new FilmDurationException("movie duration cannot be negative or equal to 0");
        }
        film.getNextId();
        films.put(film.getId(), film);
        log.info("Operation success: Saved create new object" + film.getName());
        return film;
    }

    @Override
    public Film update(Film film) {
        if (film.getDescription().length() > 200) {
            log.error("Movie description cannot exceed 200 characters");
            throw new FilmDescriptionException("Movie description cannot exceed 200 characters");
        } else if (film.getReleaseDate().isBefore(TIME)) {
            log.error("Error: movies date cannot be earlier" + TIME);
            throw new FilmDateException("movies date cannot be earlier" + TIME);
        } else if (film.getDuration().isNegative()) {
            log.error("Error: movie duration cannot be negative or equal to 0");
            throw new FilmDurationException("movie duration cannot be negative or equal to 0");
        } else if (!films.containsKey(film.getId())) {
            log.error("Error: movie with " + film.getId() + " is not in the database");
            throw new FilmDoesntExistException(" movie with \" + film.getId() + \" is not in the database");
        }
        films.put(film.getId(), film);
        log.info("Operation success: Movie is updated" + film.getName());
        return film;
    }

    @Override
    public Film delete(Film film) {
        if (!films.containsKey(film.getId())) {
            log.error("Can't found film with " + film.getName());
            throw new FilmDoesntExistException(" movie with \" + film.getId() + \" is not in the database");
        }
        films.remove(film.getId());
        log.info("Operation success: Movie is delete" + film.getName());
        return film;
    }

    @Override
    public Collection<Film> getFilms() {
        return films.values();
    }

    @Override
    public Stream<Film> getPopularFilms() {
        return films.values().stream().sorted(Comparator.comparing(Film::getRate));
    }

    public Film getFilmById(Integer filmId) {
        if (!films.containsKey(filmId)) {
            log.error("Can't found film with " + filmId);
            throw new FilmIncorrectId( " movie with \" + film.getId() + \" is not in the database");
        }
        return films.get(filmId);
    }

    public void addLike(Integer filmId, Integer userId) {

    }
}
