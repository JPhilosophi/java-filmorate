package ru.yandex.practicum.filmorate.controller.film;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@Validated
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> get() {
        return filmService.get();
    }

    @PostMapping
    public Film save(@Valid @RequestBody Film film) {
        filmService.save(film);
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        return filmService.update(film);
    }

    @GetMapping("/popular")
    @ResponseBody
    public List<Film> getPopular(@RequestParam(required=false, name="count") Integer count) {
        return filmService.getPopular(count);
    }

    @GetMapping("/{id}")
    public Film getById(@PathVariable Integer id) {
        return filmService.getById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Integer id, @PathVariable Integer userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable Integer id, @PathVariable Integer userId) {
        filmService.deleteLike(id, userId);
    }

    @DeleteMapping()
    public Film deleteFilm(@Valid @RequestBody Film film){
        return filmService.delete(film);
    }

}