package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Data
public class Film {
    private static int count = 1;
    private int id;
    @NotBlank
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private int rate;
    @JsonProperty("mpa")
    private MpaRating mpaRating;
    @JsonProperty("genres")
    private List<Genre> genres;

    public void getNextId() {
        id = count++;
    }
}