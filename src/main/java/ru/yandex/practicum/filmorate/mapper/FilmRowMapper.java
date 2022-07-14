package ru.yandex.practicum.filmorate.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class FilmRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(rs.getInt("ID"));
        film.setName(rs.getString("NAME"));
        film.setDescription(rs.getString("DESCRIPTION"));
        Date date = rs.getTimestamp("RELEASE_DATE");
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        film.setReleaseDate(instant.atZone(defaultZoneId).toLocalDate());
        film.setDuration(Duration.ofMinutes(rs.getInt("DURATION")));
        film.setRate(rs.getInt("RATE"));
        MpaRating mpaRating = new MpaRating();
        mpaRating.setId(rs.getInt("RATING_ID"));
        film.setMpaRating(mpaRating);
        List<Genre> genres = film.getGenres();
        return film;
    }
}
