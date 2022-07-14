package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mapper.GenreRowMapper;
import ru.yandex.practicum.filmorate.mapper.UserRowMapper;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Component("genreDbStorage")
public class GenreDbStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Genre getGenreById (Integer id) {
        Genre genre = (Genre) jdbcTemplate.queryForObject("SELECT * FROM GENRE WHERE ID = ?",
                new GenreRowMapper(),
                id);
        return genre;
    }

    public List<Genre> getAll() {
        return jdbcTemplate.query("SELECT * FROM GENRE",
                (rs, rowNum) -> getGenreFromRS(rs));
    }


    private static Genre getGenreFromRS(ResultSet rs) throws SQLException {
        Genre genre = new Genre();
        genre.setId(rs.getInt("ID"));
        genre.setName(rs.getString("NAME"));
        return genre;
    }
}
