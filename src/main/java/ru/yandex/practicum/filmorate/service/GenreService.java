package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreDbStorage;

import java.util.List;

@Slf4j
@Service
public class GenreService {
    private final GenreDbStorage genreDbStorage;

    @Autowired
    public GenreService(GenreDbStorage genreDbStorage) {
        this.genreDbStorage = genreDbStorage;
    }

    public Genre getById (Integer id) {
        if (genreDbStorage.getGenreById(id) == null) {
            log.error("Not found mpa with " + id);
            throw new NotFoundException("Error: can't found");
        }
        return genreDbStorage.getGenreById(id);
    }

    public List<Genre> getAll(){
        return genreDbStorage.getAll();
    }
}
