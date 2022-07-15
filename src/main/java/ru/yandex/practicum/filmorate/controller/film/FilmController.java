package ru.yandex.practicum.filmorate.controller.film;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.interfaces.FilmInterface;
import ru.yandex.practicum.filmorate.service.services.FilmService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@Validated
@RequestMapping("/films")
public class FilmController {
    private final FilmInterface filmInterface;

    @Autowired
    public FilmController(FilmInterface filmInterface) {
        this.filmInterface = filmInterface;
    }

    @GetMapping
    public Collection<Film> get() {
        return filmInterface.get();
    }

    @PostMapping
    public Film save(@Valid @RequestBody Film film) {
        filmInterface.save(film);
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        return filmInterface.update(film);
    }

    @GetMapping("/popular")
    @ResponseBody
    public List<Film> getPopular(@RequestParam(required=false, name="count") Integer count) {
        return filmInterface.getPopular(count);
    }

    @GetMapping("/{id}")
    public Film getById(@PathVariable Integer id) {
        return filmInterface.getById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Integer id, @PathVariable Integer userId) {
        filmInterface.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable Integer id, @PathVariable Integer userId) {
        filmInterface.deleteLike(id, userId);
    }

    @DeleteMapping()
    public Film deleteFilm(@Valid Film film){
        return filmInterface.delete(film);
    }

}