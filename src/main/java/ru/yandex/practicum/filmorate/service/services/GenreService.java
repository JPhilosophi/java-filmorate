package ru.yandex.practicum.filmorate.service.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.interfaces.IGenreService;
import ru.yandex.practicum.filmorate.storage.db_storage.GenreDbStorage;

import java.util.List;

@Slf4j
@Service
public class GenreService implements IGenreService {
    private final GenreDbStorage genreDbStorage;

    @Autowired
    public GenreService(GenreDbStorage genreDbStorage) {
        this.genreDbStorage = genreDbStorage;
    }

    @Override
    public Genre getById (Integer id) {
        if (id < 1) {
            log.error("Not found mpa with " + id);
            throw new NotFoundException("Error: can't found");
        }
        if (genreDbStorage.getGenreById(id) == null) {
            log.error("Not found mpa with " + id);
            throw new NotFoundException("Error: can't found");
        }
        return genreDbStorage.getGenreById(id);
    }

    @Override
    public List<Genre> getAll(){
        return genreDbStorage.getAll();
    }
}
