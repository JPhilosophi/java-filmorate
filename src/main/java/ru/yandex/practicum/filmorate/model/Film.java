package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.DurationDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.DurationSerializer;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.Duration;
import java.time.LocalDate;

@Data
public class Film {
    private static int count = 1;
    private int id;
    @NotBlank
    private String name;
    private String description;
    private LocalDate releaseDate;
    @JsonSerialize(using = DurationSerializer.class)
    @JsonDeserialize(using = DurationDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Duration duration;
    private int rate;

    public void getNextId() {
        id = count++;
    }
}