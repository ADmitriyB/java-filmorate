package ru.yandex.practicum.filmorate.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FilmLikeException extends RuntimeException {
    private final Integer filmId;
    private final Integer userId;
}
