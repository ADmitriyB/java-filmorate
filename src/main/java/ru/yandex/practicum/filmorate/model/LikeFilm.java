package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class LikeFilm {
    private Integer filmId;
    private Integer userId;
}
