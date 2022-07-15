package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;

public interface MpaInterface {

    MpaRating getMpaById (Integer id);

    List<MpaRating> getAll();
}
