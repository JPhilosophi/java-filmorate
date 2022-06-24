package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exeption.film.FilmDateException;
import ru.yandex.practicum.filmorate.exeption.film.FilmDescriptionException;
import ru.yandex.practicum.filmorate.exeption.film.FilmDoesntExistException;
import ru.yandex.practicum.filmorate.exeption.film.FilmDurationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmControllerTest {
    private final Film film = new Film();
    private final InMemoryFilmStorage filmStorage;

    public FilmControllerTest(InMemoryFilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    @Test
    public void successCreateFilm() {
        film.setId(1);
        film.setName("Strange");
        film.setReleaseDate(LocalDate.of(1895, 12, 30));
        film.setDuration(Duration.ofMinutes(140));
        film.setDescription("some film");
        assertNotNull(filmStorage.save(film));
    }

    @Test
    public void successUpdateFilm() {
        film.setId(1);
        film.setName("Strange");
        film.setReleaseDate(LocalDate.of(1895, 12, 30));
        film.setDuration(Duration.ofMinutes(140));
        film.setDescription("some film");
        filmStorage.save(film);
        film.setId(1);
        film.setName("Strange 2");
        film.setReleaseDate(LocalDate.of(1895, 12, 30));
        film.setDuration(Duration.ofMinutes(140));
        film.setDescription("some film 2");
        assertNotNull(filmStorage.update(film));
    }

    @Test
    public void shouldReturnErrorDescriptionSize() {
        film.setId(1);
        film.setName("Strange");
        film.setReleaseDate(LocalDate.of(1895, 12, 30));
        film.setDuration(Duration.ofMinutes(140));
        film.setDescription("asdadsdqwdqwdwqedwqee232132bie12b3i21b321hk3b213b21h321h4v2k14b2k14b2k1j4bh214h21v4hvk12" +
                "asdadsdqwdqwdwqedwqee232132bie12b3i21b321hk3b213b21h321h4v2k14b2k14b2k1j4bh214h21v4hvk12asdadsdqwd" +
                "qwdwqedwqee232132bie12b3i21b321hk3b213b21h321h4v2k14b2k14b2k1j4bh214h21v4hvk12");
        assertThrows(FilmDescriptionException.class, () -> filmStorage.save(film));
    }

    @Test
    public void shouldReturnErrorByTime() {
        film.setId(1);
        film.setName("Strange");
        film.setReleaseDate(LocalDate.of(1895, 12, 1));
        film.setDuration(Duration.ofMinutes(140));
        film.setDescription("some film 2");
        assertThrows(FilmDateException.class, () -> filmStorage.save(film));
    }

    @Test
    public void shouldReturnErrorWrongDuration() {
        film.setId(1);
        film.setName("Strange");
        film.setReleaseDate(LocalDate.of(1895, 12, 1));
        film.setDuration(Duration.ofMinutes(-140));
        film.setDescription("some film 2");
        assertThrows(FilmDurationException.class, () -> filmStorage.save(film));
    }

    @Test
    public void shouldReturnErrorNotFound() {
        film.setId(1);
        film.setName("Strange");
        film.setReleaseDate(LocalDate.of(1895, 12, 1));
        film.setDuration(Duration.ofMinutes(-140));
        film.setDescription("some film 2");
        assertThrows(FilmDoesntExistException.class, () -> filmStorage.update(film));
    }
}
