package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@RestController
@Validated
@RequestMapping("/films")
public class FilmController {
    private final LocalDate TIME = LocalDate.of(1895, 12, 28);
    private final Map<Integer, Film> films = new HashMap<>();


    @GetMapping
    public Stream<Film> getFilms() {
        return films.values().stream().sorted(Comparator.comparing(Film::getRate));
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        if (film.getDescription().length() > 200) {
            log.error("Movie description cannot exceed 200 characters");
            throw new ValidationException("Длина описания не может быть больше 200 символов");
        } else if (film.getReleaseDate().isBefore(TIME)) {
            log.error("Error: movies date cannot be earlier" + TIME);
            throw new ValidationException("Дата релиза — не может быть раньше " + " " + TIME);
        } else if (film.getDuration().isNegative()) {
            log.error("Error: movie duration cannot be negative or equal to 0");
            throw new ValidationException("Продолжительность фильма не может быть отрицательной");
        }
        film.setId(films.size() + 1);
        films.put(film.getId(), film);
        log.info("Operation success: Saved create new object" + film.getName());
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) throws ValidationException {
        log.info("Запрос получен.");
        if (film.getDescription().length() > 200) {
            log.error("Movie description cannot exceed 200 characters");
            throw new ValidationException("Длина описания не может быть больше 200 символов");
        } else if (film.getReleaseDate().isBefore(TIME)) {
            log.error("Error: movies date cannot be earlier" + TIME);
            throw new ValidationException("Дата релиза — не может быть раньше " + TIME);
        } else if (film.getDuration().isNegative()) {
            log.error("Error: movie duration cannot be negative or equal to 0");
            throw new ValidationException("Продолжительность фильма не может быть отрицательной");
        } else if (!films.containsKey(film.getId())) {
            log.error("Error: movie with " + film.getId() + " is not in the database");
            throw new ValidationException("Фильма с " + film.getId() + " нет в базе");
        }
        films.put(film.getId(), film);
        log.info("Operation success: Movie is updated" + film.getName());
        return film;
    }
}