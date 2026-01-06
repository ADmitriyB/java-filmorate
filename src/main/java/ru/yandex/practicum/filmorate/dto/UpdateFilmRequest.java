package ru.yandex.practicum.filmorate.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateFilmRequest {
    private String name;
    private LocalDate releaseDate;
    private Integer mpaId;
    private Integer genreId;

    public boolean hasName() {
        return ! (name == null || name.isBlank());
    }

    public boolean hasReleaseDate() {
        return ! (releaseDate == null);
    }

    public boolean hasMpaId() {
        return ! (mpaId == null);
    }

    public boolean hasGenreId() {
        return ! (genreId == null);
    }
}
