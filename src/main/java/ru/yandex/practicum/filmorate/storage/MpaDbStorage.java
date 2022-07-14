package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Component("mpaDbStorage")
public class MpaDbStorage {
    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public MpaRating getMpaById (Integer id) {
        List<MpaRating> mpa = jdbcTemplate.query("SELECT * FROM FILM_RATING WHERE ID = ?",
                (rs, rowNum) -> getMpaFromRS(rs), id);
        MpaRating mpaRating = mpa.get(0);
        return mpaRating;
    }

    public List<MpaRating> getAll() {
        return jdbcTemplate.query("SELECT * FROM FILM_RATING",
                (rs, rowNum) -> getMpaFromRS(rs));
    }


    private static MpaRating getMpaFromRS(ResultSet rs) throws SQLException {
        MpaRating mpa = new MpaRating();
        mpa.setId(rs.getInt("ID"));
        mpa.setMpaRating(rs.getString("MPA_RATING"));
        return mpa;
    }
}
