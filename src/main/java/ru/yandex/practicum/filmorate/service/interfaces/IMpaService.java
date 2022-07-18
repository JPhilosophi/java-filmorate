package ru.yandex.practicum.filmorate.service.interfaces;

import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;

public interface IMpaService {

    MpaRating getById (Integer id);

    List<MpaRating> getAll();
}
