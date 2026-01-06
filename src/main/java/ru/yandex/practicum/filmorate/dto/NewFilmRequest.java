package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.MinimumDate;

import java.time.LocalDate;

@Data
public class NewFilmRequest {

    private Integer id;
    private String name;
    @Size(max = 200)
    private String description;
    @Positive
    private int duration;
    @MinimumDate
    private LocalDate releaseDate;
    private Mpa mpa;
    private Genre genre;

    @Data
    public static class Mpa {
        private Integer MpaId;
    }

    @Data
    public static class Genre {
        private Integer genreId;
    }
}