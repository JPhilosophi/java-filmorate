package ru.yandex.practicum.filmorate.controller.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.interfaces.IGenreService;

import java.util.List;

@RestController
@Validated
@RequestMapping("/genres")
public class GenreController {
    private final IGenreService genreInterface;

    @Autowired
    public GenreController(IGenreService genreInterface) {
        this.genreInterface = genreInterface;
    }

    @GetMapping("/{id}")
    public Genre getById (@PathVariable Integer id) {
        return genreInterface.getById(id);
    }

    @GetMapping
    public List<Genre> getAll () {
        return genreInterface.getAll();
    }
}
