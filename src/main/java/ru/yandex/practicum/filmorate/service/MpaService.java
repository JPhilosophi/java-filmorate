package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.MpaDbStorage;

import java.util.List;

@Slf4j
@Service
public class MpaService {
    private final MpaDbStorage mpaDbStorage;

    @Autowired
    public MpaService(MpaDbStorage mpaDbStorage) {
        this.mpaDbStorage = mpaDbStorage;
    }

    public MpaRating getById (Integer id) {
        if (id < 0) {
            log.error("Not found mpa with " + id);
            throw new NotFoundException("Error: can't found");
        } else if (mpaDbStorage.getMpaById(id) == null) {
            log.error("Not found mpa with " + id);
            throw new NotFoundException("Error: can't found");
        }
        return mpaDbStorage.getMpaById(id);
    }

    public List<MpaRating> getAll(){
        return mpaDbStorage.getAll();
    }
}
