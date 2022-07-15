package ru.yandex.practicum.filmorate.controller.film;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.interfaces.MpaInterface;
import ru.yandex.practicum.filmorate.service.services.MpaService;

import java.util.List;

@RestController
@Validated
@RequestMapping("/mpa")
public class MpaRatingController {
    private final MpaInterface mpaInterface;

    @Autowired
    public MpaRatingController(MpaInterface mpaInterface) {
        this.mpaInterface = mpaInterface;
    }

    @GetMapping("/{id}")
    public MpaRating getById (@PathVariable Integer id) {
        return mpaInterface.getById(id);
    }

    @GetMapping
    public List<MpaRating> getAll () {
        return mpaInterface.getAll();
    }
}
