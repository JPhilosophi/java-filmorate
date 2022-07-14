package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@Component("filmDbStorage")
public class FilmDbStorage implements FilmStorage{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film save(Film film) {
        MpaRating mpaRating = film.getMpaRating();
        List<Genre> genres = film.getGenres();
        jdbcTemplate.update("INSERT INTO FILMS (NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATE, MPA_RATING) " +
                "VALUES ( ?,?,?,?,?,? )",
                film.getId(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRate(),
                mpaRating.getId());
        for (Genre i : film.getGenres()) {
            jdbcTemplate.update("UPDATE FILM_GENRES set FILM_ID = ?," +
                    "GENRE_ID = ?", film.getId(), i.getId());
        }
        return film;
    }

    @Override
    public Film update(Film film) {
        checkingFilmForUpdate(film);
        String SQL = "UPDATE FILMS set NAME = ?," +
                     "DESCRIPTION = ?," +
                     "RELEASE_DATE = ?," +
                     "DURATION = ?," +
                     "RATE = ?," +
                     "MPA_RATING = ? " +
                     "WHERE ID = ?";
        jdbcTemplate.update(SQL,  film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getRate(), film.getMpaRating());
        for (Genre i : film.getGenres()) {
            jdbcTemplate.update("UPDATE FILM_GENRES set FILM_ID = ?," +
                    "GENRE_ID = ?", film.getId(), i.getId());
        }
        return film;
    }

    @Override
    public Film delete(Film film) {
        String SQL = "DELETE FROM FILMS WHERE id = ?";
        jdbcTemplate.update(SQL, film.getId());
        return film;
    }

    @Override
    public Map<Integer, Film> getFilms() {
        Map<Integer, Film> result = new HashMap<>();
        jdbcTemplate.query("SELECT * FROM FILMS", (rs, rowNum) -> {
            Film film = getFilmFromRS(rs);
            result.put(film.getId(), film);
            return null;
        });
        return result;
    }

    @Override
    public Map<Integer, Set<Integer>> getLikes() {
        Map<Integer, Set<Integer>> likes = new HashMap<>();
        jdbcTemplate.query("SELECT * FROM FILM_LIKES WHERE FILM_ID = ? GROUP BY FILM_ID", (rs, rowNum) -> {
            likes.put(rs.getInt("FILM_ID"), (Set<Integer>) rs.getArray("USER_ID"));
            return null;
        });
        return likes;
    }

    @Override
    public void addLike(Integer filmId, Integer userId) {
        jdbcTemplate.update("insert into FILM_LIKES (FILM_ID,USER_ID) values (?,?);"
                ,filmId, userId);
    }

    @Override
    public void deleteLike(Integer filmId, Integer userId) {
        jdbcTemplate.update("DELETE FROM FILM_LIKES WHERE FILM_ID =? AND USER_ID = ?",
                filmId, userId);
    }

    private void checkingFilmForUpdate(Film film) {
        String SQL = "select * from FILM where ID = ?";
        Film film1 = (Film) jdbcTemplate.queryForObject(SQL, new Object[]{film.getId()}, new FilmRowMapper());
        if (film1 == null) {
            log.error("Not found user" + " " + film1.getId());
            throw new NotFoundException("Error: can't found user" + film1.getId());
        }
    }


    private static Film getFilmFromRS(ResultSet rs) throws SQLException {
        Film film = new Film();
        film.setId(rs.getInt("ID"));
        film.setName(rs.getString("NAME"));
        film.setDescription(rs.getString("DESCRIPTION"));
        Date date = rs.getTimestamp("RELEASE_DATE");
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        film.setReleaseDate(instant.atZone(defaultZoneId).toLocalDate());
        film.setRate(rs.getInt("RATE"));
        MpaRating mpaRating = film.getMpaRating();
        film.setMpaRating(mpaRating);
        List<Genre> genreList = film.getGenres();
        return film;
    }
}
