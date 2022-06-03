package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

public @Data
class User {
    private int id;
    @Email(message = "Incorrect email format")
    @NotNull(message = "Please enter email")
    @NotEmpty(message = "Please enter email")
    private String email;
    @NotNull(message = "Login can't be null")
    private String login;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;
}
