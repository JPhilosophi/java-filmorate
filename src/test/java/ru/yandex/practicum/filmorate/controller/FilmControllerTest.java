package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.yandex.practicum.filmorate.exeption.BadRequestException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FilmControllerTest {
    private final Film film = new Film();
    @Autowired
    @Qualifier("filmDbStorage")
    private FilmStorage filmStorage;


    @Test
    public void successCreateFilm() {
        film.setId(1);
        film.setName("Strange");
        film.setReleaseDate(LocalDate.of(1895, 12, 30));
        film.setDuration(140);
        film.setDescription("some film");
        assertNotNull(filmStorage.save(film));
    }

    @Test
    public void successUpdateFilm() {
        film.setId(1);
        film.setName("Strange");
        film.setReleaseDate(LocalDate.of(1895, 12, 30));
        film.setDuration(140);
        film.setDescription("some film");
        filmStorage.save(film);
        film.setId(1);
        film.setName("Strange 2");
        film.setReleaseDate(LocalDate.of(1895, 12, 30));
        film.setDuration(140);
        film.setDescription("some film 2");
        assertNotNull(filmStorage.update(film));
    }

    @Test
    public void shouldReturnErrorDescriptionSize() {
        film.setId(1);
        film.setName("Strange");
        film.setReleaseDate(LocalDate.of(1895, 12, 30));
        film.setDuration(140);
        film.setDescription("asdadsdqwdqwdwqedwqee232132bie12b3i21b321hk3b213b21h321h4v2k14b2k14b2k1j4bh214h21v4hvk12" +
                "asdadsdqwdqwdwqedwqee232132bie12b3i21b321hk3b213b21h321h4v2k14b2k14b2k1j4bh214h21v4hvk12asdadsdqwd" +
                "qwdwqedwqee232132bie12b3i21b321hk3b213b21h321h4v2k14b2k14b2k1j4bh214h21v4hvk12");
        assertThrows(BadRequestException.class, () -> filmStorage.save(film));
    }

    @Test
    public void shouldReturnErrorByTime() {
        film.setId(1);
        film.setName("Strange");
        film.setReleaseDate(LocalDate.of(1895, 12, 1));
        film.setDuration(140);
        film.setDescription("some film 2");
        assertThrows(BadRequestException.class, () -> filmStorage.save(film));
    }

    @Test
    public void shouldReturnErrorWrongDuration() {
        film.setId(1);
        film.setName("Strange");
        film.setReleaseDate(LocalDate.of(1895, 12, 1));
        film.setDuration(-140);
        film.setDescription("some film 2");
        assertThrows(BadRequestException.class, () -> filmStorage.save(film));
    }

    @Test
    public void shouldReturnErrorNotFound() {
        film.setId(1);
        film.setName("Strange");
        film.setReleaseDate(LocalDate.of(1895, 12, 1));
        film.setDuration(-140);
        film.setDescription("some film 2");
        assertThrows(BadRequestException.class, () -> filmStorage.update(film));
    }

    @Test
    public void shouldReturnErrorNotFound2() {
        Genre genre1 = new Genre();
        Genre genre2 = new Genre();
        Genre genre3 = new Genre();
        genre1.setId(1);
        genre2.setId(2);
        genre3.setId(1);

        List<Genre> genreList = new ArrayList<>();
        genreList.add(genre1);
        genreList.add(genre2);
        genreList.add(genre3);
        Set<Genre> genres = new HashSet<>(genreList);
        assertEquals(2, genres.size());
    }
}
