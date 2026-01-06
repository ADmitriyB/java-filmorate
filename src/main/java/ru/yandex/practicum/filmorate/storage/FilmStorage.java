package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Film addFilm(Film film);

    void removeFilm(Integer filmId);

    Film updateFilm(Film film);

    Film findFilmById(Integer filmId);

    Collection<Film> getAllFilms();

    Collection<Film> findFilmsByName(String name);

    Collection<Film> findFilmsByYear(int year);

    int countFilms();

}
