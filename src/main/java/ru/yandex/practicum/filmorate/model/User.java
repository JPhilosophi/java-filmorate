package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class User {
    private int id = 0;
    @Email(message = "Incorrect email format")
    @NotNull(message = "Please enter email")
    @NotEmpty(message = "Please enter email")
    private String email;
    @NotNull(message = "Login can't be null")
    private String login;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    public void getNextId() {
        id++;
    }
}