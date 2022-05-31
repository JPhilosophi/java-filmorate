package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FilmControllerTest {

    @Test
    public void testGetPostAndPutMethods() throws ValidationException {
        FilmController filmController = new FilmController();
        Film film = new Film();
        film.setId(1);
        film.setName("Star");
        film.setDescription("Some vanila film");
        film.setReleaseDate(LocalDate.of(1990, 10, 1));
        film.setDuration(Duration.ofMinutes(140));
        filmController.create(film);
        Collection<Film> filmList = filmController.getTop5Films();
        assertEquals(filmList.size(), 1);
        assertTrue(filmController.getTop5Films().contains(film));
    }
}
