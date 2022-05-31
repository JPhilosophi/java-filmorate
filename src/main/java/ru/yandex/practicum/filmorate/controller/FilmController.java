package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final List<Film> films = new ArrayList<>();
    private final  LocalDate TIME = LocalDate.of(1895, 12, 28);


    @GetMapping
    public List<Film> getFilms(){
        return films;
    }

    @PostMapping
    public Film create(@RequestBody Film film) throws ValidationException {
        if (film.getName().isBlank()) {
            throw new ValidationException("Название фильма не может быть пустым");
        } else if (film.getDescription().length() > 200) {
            throw new ValidationException("Длина описания не может быть больше 200 символов");
        } else if (film.getReleaseDate().isBefore(TIME)) {
            throw new ValidationException("Дата релиза — не может быть раньше " + TIME);
        } else if (film.getDuration().isNegative()) {
            throw new ValidationException("Продолжительность фильма не может быть отрицательной");
        } else {
            films.add(film);
        }
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) throws ValidationException {
        log.info("Запрос получен.");
        if (film.getName().isBlank()) {
            throw new ValidationException("Название фильма не может быть пустым");
        } else if (film.getDescription().length() > 200) {
            throw new ValidationException("Длина описания не может быть больше 200 символов");
        } else if (film.getReleaseDate().isBefore(TIME)) {
            throw new ValidationException("Дата релиза — не может быть раньше " + TIME);
        } else if (film.getDuration().isNegative()) {
            throw new ValidationException("Продолжительность фильма не может быть отрицательной");
        } else {
            films.add(film);
        }
        return film;
    }
}
