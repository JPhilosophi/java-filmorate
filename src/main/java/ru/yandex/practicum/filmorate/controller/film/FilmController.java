package ru.yandex.practicum.filmorate.controller.film;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.interfaces.IFilmService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@Validated
@RequestMapping("/films")
public class FilmController {
    private final IFilmService IFilmService;

    @Autowired
    public FilmController(IFilmService IFilmService) {
        this.IFilmService = IFilmService;
    }

    @GetMapping
    public Collection<Film> get() {
        return IFilmService.get();
    }

    @PostMapping
    public Film save(@Valid @RequestBody Film film) {
        IFilmService.save(film);
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        return IFilmService.update(film);
    }

    @GetMapping("/popular")
    @ResponseBody
    public List<Film> getPopular(@RequestParam(required=false, name="count") Integer count) {
        return IFilmService.getPopular(count);
    }

    @GetMapping("/{id}")
    public Film getById(@PathVariable Integer id) {
        return IFilmService.getById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Integer id, @PathVariable Integer userId) {
        IFilmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable Integer id, @PathVariable Integer userId) {
        IFilmService.deleteLike(id, userId);
    }

    @DeleteMapping()
    public Film deleteFilm(@Valid Film film){
        return IFilmService.delete(film);
    }

}