package ru.yandex.practicum.filmorate.service.interfaces;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface IGenreService {

    Genre getById (Integer id);

    List<Genre> getAll();
}
