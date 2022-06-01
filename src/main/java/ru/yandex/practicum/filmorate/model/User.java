package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public @Data class User {
    private int id;
    @NotNull
    @NotBlank
    private String email;
    @NotNull
    @NotBlank
    private String login;
    private String name;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;
}
