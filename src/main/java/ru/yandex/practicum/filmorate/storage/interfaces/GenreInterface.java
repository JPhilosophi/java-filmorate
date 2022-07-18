package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreInterface {
    Genre getGenreById (Integer id);

    List<Genre> getAll();
}
