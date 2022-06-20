package ru.yandex.practicum.filmorate.controller.film;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Stream;

@RestController
@Validated
@RequestMapping("/films")
public class FilmController {
    private final InMemoryFilmStorage filmStorage;

    @Autowired
    public FilmController(InMemoryFilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    @GetMapping
    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        filmStorage.add(film);
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        filmStorage.update(film);
        return film;
    }

    @GetMapping("/popular")
    public Stream<Film> getPopular() {
        return filmStorage.getPopularFilms();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@RequestBody @PathVariable Integer id) {
        return filmStorage.getFilmById(id);
    }
}