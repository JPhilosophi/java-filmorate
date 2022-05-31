package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final  LocalDate TIME = LocalDate.of(1895, 12, 28);
    private final Map<Integer, Film> films = new HashMap<>();


    @GetMapping
    public Collection<Film> getTop5Films() {
        List<Film> rate = new ArrayList<>(films.values());
        Collections.sort(rate);
        List<Film> sorted = new ArrayList<>();
        for (int i = 0 ; i < rate.size(); i++){
            if (i == 5) {
                return sorted;
            }
            sorted.add(rate.get(i));
        }
        return sorted;
    }

    @PostMapping
    public Film create(@RequestBody Film film) throws ValidationException {
        log.info("Запрос получен.");
        if (film.getName().isBlank() | film.getName().isEmpty()) {
            throw new ValidationException("Название фильма не может быть пустым");
        } else if (film.getDescription().length() > 200) {
            throw new ValidationException("Длина описания не может быть больше 200 символов");
        } else if (film.getReleaseDate().isBefore(TIME)) {
            throw new ValidationException("Дата релиза — не может быть раньше " + TIME);
        } else if (film.getDuration().isNegative()) {
            throw new ValidationException("Продолжительность фильма не может быть отрицательной");
        }
        film.setId(films.size() + 1);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) throws ValidationException {
        log.info("Запрос получен.");
        if (film.getName().isBlank() | film.getName().isEmpty()) {
            throw new ValidationException("Название фильма не может быть пустым");
        } else if (film.getDescription().length() > 200) {
            throw new ValidationException("Длина описания не может быть больше 200 символов");
        } else if (film.getReleaseDate().isBefore(TIME)) {
            throw new ValidationException("Дата релиза — не может быть раньше " + TIME);
        } else if (film.getDuration().isNegative()) {
            throw new ValidationException("Продолжительность фильма не может быть отрицательной");
        } else if (!films.containsKey(film.getId())) {
            throw new ValidationException("Ошибка обновления фильма с " + film.getId() + "нет в базе");
        }
        film.setId(films.size() + 1);
        films.put(film.getId(), film);
        return film;
    }
}
