package ru.yandex.practicum.filmorate.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FilmLikeException extends RuntimeException {
    private final long filmId;
    private final long userId;
}
