package ru.yandex.practicum.filmorate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;


@SpringBootApplication
public class FilmorateApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilmorateApplication.class, args);
//        var ctx =
//        var template = ctx.getBean(JdbcTemplate.class);
//        template.query("SELECT * FROM USERS", new ResultSetExtractor<Object>() {
//            @Override
//            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
//                System.out.println(rs.getFetchSize());
//                return null;
//            }
//        });

        
    }

}
