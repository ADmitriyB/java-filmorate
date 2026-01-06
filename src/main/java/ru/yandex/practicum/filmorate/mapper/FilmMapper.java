package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FilmMapper {
    public static Film mapToFilm(NewFilmRequest request) {
        Film film = new Film();
        film.setId(request.getId());
        film.setName(request.getName());
        film.setDescription(request.getDescription());
        film.setDuration(request.getDuration());
        film.setReleaseDate(LocalDate.from(request.getReleaseDate()));
        film.setMpaId(request.getMpa().getMpaId());
        film.setGenreId(request.getGenre().getGenreId());
        return film;
    }

    public static FilmDto mapToFilmDto(Film film) {
        FilmDto dto = new FilmDto();
        dto.setId(film.getId());
        dto.setName(film.getName());
        dto.setDescription(film.getDescription());
        dto.setDuration(film.getDuration());
        dto.setReleaseDate(LocalDate.from(film.getReleaseDate()));
        dto.setMpaId(film.getMpaId());
        dto.setGenreId(film.getGenreId());

        return dto;
    }

    public static Film updateFilmFields(Film film, UpdateFilmRequest request) {
        if (request.hasName()) {
            film.setName(request.getName());
        }
        if (request.hasReleaseDate()) {
            film.setReleaseDate(LocalDate.from(request.getReleaseDate()));
        }
        if (request.hasMpaId()) {
            film.setMpaId(request.getMpaId());
        }
        if (request.hasGenreId()) {
            film.setGenreId(request.getGenreId());
        }

        return film;
    }
}

