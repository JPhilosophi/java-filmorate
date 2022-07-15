package ru.yandex.practicum.filmorate.storage.db_storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component("filmDbStorage")
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film save(Film film) {
        film.getNextId();
        MpaRating mpaRating = film.getMpaRating();
        jdbcTemplate.update("INSERT INTO FILMS (ID, NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATE, MPA_RATING) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)",
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRate(),
                mpaRating.getId());
        Optional.ofNullable(film.getGenres()).ifPresent(genres -> {
            jdbcTemplate.batchUpdate(
                    "INSERT INTO FILM_GENRES(FILM_ID, GENRE_ID) VALUES (?, ?)",
                    genres,
                    genres.size(),
                    (ps, item) -> {
                        ps.setInt(1, film.getId());
                        ps.setInt(2, item.getId());
                    });
            }
        );
        return film;
    }

    @Override
    public Film update(Film film) {
        checkingFilmForUpdate(film);
        MpaRating mpaRating = film.getMpaRating();
        String SQL = "UPDATE FILMS set NAME = ?," +
                     "DESCRIPTION = ?," +
                     "RELEASE_DATE = ?," +
                     "DURATION = ?," +
                     "RATE = ?," +
                     "MPA_RATING = ? " +
                     "WHERE ID = ?";
        jdbcTemplate.update(SQL,  film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getRate(), mpaRating.getId(), film.getId());
        Optional.ofNullable(film.getGenres()).ifPresent(genres -> {
            jdbcTemplate.update("DELETE FROM FILM_GENRES WHERE FILM_ID = ?", film.getId());
            Set<Genre> genreSet = new HashSet<>(genres);
            jdbcTemplate.batchUpdate(
                    "INSERT INTO FILM_GENRES(FILM_ID, GENRE_ID) VALUES (?, ?)",
                    genreSet,
                    genreSet.size(),
                    (ps, item) -> {
                        ps.setInt(1, film.getId());
                        ps.setInt(2, item.getId());
                    });
        });
        return getFilmById(film.getId());
    }

    @Override
    public Film delete(Film film) {
        String SQL = "DELETE FROM FILMS WHERE id = ?";
        jdbcTemplate.update(SQL, film.getId());
        return film;
    }

    @Override
    public Map<Integer, Film> getFilms(Integer count) {
        Map<Integer, Film> result = new HashMap<>();
        jdbcTemplate.query("SELECT F.ID, F.NAME, F.DESCRIPTION, F.RELEASE_DATE, F.DURATION, F.RATE, MR.ID AS MPA_ID, MR.MPA_RATING " +
                "FROM FILMS F " +
                "LEFT JOIN MPA_RATING MR on MR.ID = F.MPA_RATING",
            (rs, rowNum) -> {
                Film film = getFilmFromRS(rs);
                result.put(film.getId(), film);
                return null;
        });
        //Существует ли более изящный способ?
        jdbcTemplate.query("SELECT FILM_ID, GENRE_ID, G2.NAME FROM FILM_GENRES " +
                "LEFT JOIN GENRE G2 on G2.ID = FILM_GENRES.GENRE_ID ", (rs, rowNum) -> {
            Integer id = rs.getInt("FILM_ID");
            Film film = result.get(id);
            if (film.getGenres() == null){
                film.setGenres(new ArrayList<>());
            }
            Genre genre = new Genre();
            genre.setId(rs.getInt("GENRE_ID"));
            genre.setName(rs.getString("NAME"));
            film.getGenres().add(genre);
            return null;
        });
//        if (count == null) {
//            result.values()
//                    .stream()
//                    .sorted(Comparator.comparing(Film::getRate))
//                    .collect(Collectors.toList());
//        } else {
//            Map<Integer, Set<Integer>> result1 = new TreeMap<>(getLikes());
//            return (Map<Integer, Film>) result.keySet().stream()
//                    .map(getFilms(null)::get)
//                    .limit(Objects.requireNonNullElse(count, 10))
//                    .collect(Collectors.toList());
//        }
        return result;
    }

    @Override
    public Map<Integer, Set<Integer>> getLikes() {
        Map<Integer, Set<Integer>> likes = new HashMap<>();
        jdbcTemplate.query("SELECT FILM_ID, USER_ID FROM FILM_LIKES",
                (rs, rowNum) -> {
                    Integer filmId = rs.getInt("FILM_ID");
                    Integer userId = rs.getInt("USER_ID");
                    if (!likes.containsKey(filmId)) {
                        likes.put(filmId, new HashSet<>());
                    }
                    likes.get(filmId).add(userId);
            return null;
        });
        return likes;
    }

    @Override
    public void addLike(Integer filmId, Integer userId) {
        jdbcTemplate.update("INSERT INTO FILM_LIKES (FILM_ID,USER_ID) values (?,?);"
                ,filmId, userId);
    }

    @Override
    public void deleteLike(Integer filmId, Integer userId) {
        jdbcTemplate.update("DELETE FROM FILM_LIKES WHERE FILM_ID =? AND USER_ID = ?",
                filmId, userId);
    }

    private void checkingFilmForUpdate(Film film) {
        String SQL = "select * from FILMS where ID = ?";
        Film film1 = (Film) jdbcTemplate.queryForObject(SQL, new Object[]{film.getId()}, new FilmRowMapper());
        if (film1 == null) {
            log.error("Not found user" + " " + film1.getId());
            throw new NotFoundException("Error: can't found user" + film1.getId());
        }
    }

    private Film getFilmById (Integer filmId) {
        Map<Integer, Film> result = new HashMap<>();
        jdbcTemplate.query("SELECT F.ID, F.NAME, F.DESCRIPTION, F.RELEASE_DATE, F.DURATION, F.RATE, MR.ID AS MPA_ID, MR.MPA_RATING " +
                        "FROM FILMS F " +
                        "LEFT JOIN MPA_RATING MR on MR.ID = F.MPA_RATING WHERE F.ID = ?",
                (rs, rowNum) -> {
                    Film film = getFilmFromRS(rs);
                    result.put(film.getId(), film);
                    return null;
                }, filmId);
        jdbcTemplate.query("SELECT FILM_ID, GENRE_ID, G2.NAME FROM FILM_GENRES " +
                "LEFT JOIN GENRE G2 on G2.ID = FILM_GENRES.GENRE_ID  WHERE FILM_ID = ?", (rs, rowNum) -> {
            Integer id = rs.getInt("FILM_ID");
            Film film = result.get(id);
            if (film.getGenres() == null){
                film.setGenres(new ArrayList<>());
            }
            Genre genre = new Genre();
            genre.setId(rs.getInt("GENRE_ID"));
            genre.setName(rs.getString("NAME"));
            film.getGenres().add(genre);
            return null;
        }, filmId);
        return result.get(filmId);
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
        film.setDuration(rs.getInt("DURATION"));
        film.setRate(rs.getInt("RATE"));
        MpaRating mpaRating = new MpaRating();
        mpaRating.setId(rs.getInt("MPA_ID"));
        mpaRating.setName(rs.getString("MPA_RATING"));
        film.setMpaRating(mpaRating);
        return film;
    }
}
