package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.FilmLikeException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final Comparator<Film> filmLikesComparator = Comparator.comparing((Film film) -> film.getUserIdsLikes().size());

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public Collection<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film findFilmById(long id) {
        return filmStorage.findFilmById(id);
    }

    public Map<String, String> addLike(Long filmId, Long userId) {
        if (!userStorage.getAllUsers().contains(userStorage.findUserById(userId))) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
        Set<Long> filmLikes = new HashSet<>(filmStorage.findFilmById(filmId).getUserIdsLikes());
        if (filmLikes.contains(userId)) {
            throw new FilmLikeException(filmId, userId);
        }
        filmLikes.add(userId);
        filmStorage.findFilmById(filmId).setUserIdsLikes(filmLikes);
        return Map.of("result", String.format("Пользователь с id %d поставил лайк фильму с id %d", userId, filmId));
    }

    public Map<String, String> removeLike(Long filmId, Long userId) {
        if (!userStorage.getAllUsers().contains(userStorage.findUserById(userId))) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
        Set<Long> filmLikes = new HashSet<>(filmStorage.findFilmById(filmId).getUserIdsLikes());
        if (!filmLikes.contains(userId)) {
            throw new FilmLikeException(filmId, userId);
        }
        filmLikes.remove(userId);
        filmStorage.findFilmById(filmId).setUserIdsLikes(filmLikes);
        return Map.of("result", String.format("Пользователь с id %d убрал лайк с фильма с id %d", userId, filmId));
    }

    public List<Film> getTopFilms(Integer count) {
        if (count == null) {
            count = 10;
        }
        if (count <= 0) {
            throw new ConditionsNotMetException("count <= 0");
        }
        return filmStorage.getAllFilms().stream()
                .sorted(filmLikesComparator.reversed())
                .limit(count)
                .toList();
    }
}
