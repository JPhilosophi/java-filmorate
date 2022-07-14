package ru.yandex.practicum.filmorate.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

public class UserRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("ID"));
        user.setName(rs.getString("NAME"));
        user.setLogin(rs.getString("LOGIN"));
        Date date = rs.getTimestamp("BIRTHDAY");
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        user.setBirthday(instant.atZone(defaultZoneId).toLocalDate());
        user.setEmail(rs.getString("EMAIL"));
        return user;
    }
}
