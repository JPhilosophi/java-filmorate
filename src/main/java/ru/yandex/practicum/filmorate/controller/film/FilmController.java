package ru.yandex.practicum.filmorate.controller.film;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
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
    @ResponseBody
    public List<Film> getPopularFilms(@RequestParam(required=false, name="count") Integer count) {
        return filmStorage.getPopularFilms(count);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Integer id) {
        return filmStorage.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Integer id, @PathVariable Integer userId) {
        filmStorage.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable Integer id, @PathVariable Integer userId) {
        filmStorage.deleteLike(id, userId);
    }
}