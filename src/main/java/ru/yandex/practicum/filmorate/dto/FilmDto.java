package ru.yandex.practicum.filmorate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FilmDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;
    private String name;
    private String description;
    private int duration;
    private LocalDate releaseDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer mpaId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer genreId;
}
