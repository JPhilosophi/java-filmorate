package ru.yandex.practicum.filmorate.storage.db_storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.BadRequestException;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@Component("userDbStorage")
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User create(User user) {
        user.getNextId();
        jdbcTemplate.update("insert into users (login,name, birthday, email) values (?,?,?,?);"
                ,user.getLogin(), user.getName(), user.getBirthday(), user.getEmail());
        return user;
    }

    @Override
    public User update(User user) {
        String SQL = "UPDATE users set login = ?, name = ?, birthday = ?, email = ? WHERE id = ?";
        jdbcTemplate.update(SQL,  user.getLogin(), user.getName(), user.getBirthday(), user.getEmail(), user.getId());
        return user;
    }

    @Override
    public User delete(User user) {
        String SQL = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(SQL, user.getId());
        return user;
    }

    @Override
    public Map<Integer, User> getUsers() {
        Map<Integer, User> result = new HashMap<>();
        jdbcTemplate.query("SELECT * FROM users", (rs, rowNum) -> {
            User user = getUserFromRS(rs);
            result.put(user.getId(), user);
            return null;
        });
        return result;
    }

    @Override
    public void addFriend(Integer id, Integer friendId) {
        jdbcTemplate.update("insert into FRIENDS (USER_ID,FRIEND_ID, STATUS) values (?,?,?);"
                ,id, friendId, 1);
    }

    @Override
    public List<User> getFriends(Integer userId) {
        return jdbcTemplate.query("SELECT u.id, u.login, u.name, u.BIRTHDAY, u.EMAIL FROM friends AS fr\n" +
                        "LEFT JOIN users AS u\n" +
                        "ON fr.friend_id = u.id\n" +
                        "WHERE fr.user_id = ?\n" +
                        "AND fr.STATUS = 1",
                (rs, rowNum) -> getUserFromRS(rs),
                userId);
    }

    @Override
    public List<User> getCommonFriendList(Integer userId, Integer otherId) {
        return jdbcTemplate.query("SELECT u.id, u.login, u.name, u.BIRTHDAY, u.EMAIL " +
                "FROM FRIENDS AS FR\n" +
                "INNER JOIN FRIENDS AS F\n" +
                "ON FR.FRIEND_ID = F.FRIEND_ID\n" +
                "LEFT JOIN USERS AS U\n" +
                "ON FR.FRIEND_ID = U.ID\n" +
                "WHERE FR.USER_ID = ?\n" +
                "AND F.USER_ID = ?",
                (rs, rowNum) -> getUserFromRS(rs),
                userId, otherId);
    }

    @Override
    public void deleteFriend(Integer userId, Integer friendId) {
        jdbcTemplate.update("DELETE FROM FRIENDS \n" +
                "WHERE USER_ID = ? \n" +
                "AND FRIEND_ID =?", userId, friendId);
    }

    private static User getUserFromRS(ResultSet rs) throws SQLException {
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
