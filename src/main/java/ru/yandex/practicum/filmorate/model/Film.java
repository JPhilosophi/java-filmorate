package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class Film {
    private static int count = 1;
    private int id;
    @NotBlank
    @NotNull
    private String name;
    @NotNull
    private String description;
    @DateTimeFormat
    private LocalDate releaseDate;
    @Min(1)
    private Integer duration;
    private int rate;
    @JsonProperty("mpa")
    @NotNull
    private MpaRating mpaRating;
    @JsonProperty("genres")
    private List<Genre> genres = new ArrayList<>();

    public void getNextId() {
        id = count++;
    }
}