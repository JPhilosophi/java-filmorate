package ru.yandex.practicum.filmorate.storage.db_storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mapper.MpaRowMapper;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.interfaces.MpaInterface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Component("mpaDbStorage")
public class MpaDbStorage implements MpaInterface {
    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MpaRating getMpaById (Integer id) {
        MpaRating mpaRating = (MpaRating) jdbcTemplate.queryForObject("SELECT * FROM MPA_RATING WHERE ID = ?",
                new MpaRowMapper(),
                id);
        return mpaRating;
    }

    @Override
    public List<MpaRating> getAll() {
        return jdbcTemplate.query("SELECT * FROM MPA_RATING",
                (rs, rowNum) -> getMpaFromRS(rs));
    }


    private static MpaRating getMpaFromRS(ResultSet rs) throws SQLException {
        MpaRating mpa = new MpaRating();
        mpa.setId(rs.getInt("ID"));
        mpa.setName(rs.getString("MPA_RATING"));
        return mpa;
    }
}
