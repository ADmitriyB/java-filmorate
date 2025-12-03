package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public Film addFilm(Film film) {
        // проверяем выполнение необходимых условий
        if (film.getName() == null || film.getName().isBlank()) {
            throw new ConditionsNotMetException("название не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            throw new ConditionsNotMetException("максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ConditionsNotMetException("дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() <= 0) {
            throw new ConditionsNotMetException("продолжительность фильма должна быть положительным числом");
        }
        // формируем дополнительные данные
        film.setId(getNextId());

        // сохраняем новую публикацию в памяти приложения
        films.put(film.getId(), film);
        log.info("Film {} with id = {} was added", film.getName(), film.getId());

        return film;
    }

    @Override
    public void removeFilm(long filmId) {
        films.remove(filmId);
    }

    @Override
    public Film updateFilm(Film newFilm) {
        if (newFilm.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }

        if (films.containsKey(newFilm.getId())) {
            Film oldFilm = films.get(newFilm.getId());
            if (!(newFilm.getName() == null || newFilm.getName().isBlank())) {
                oldFilm.setName(newFilm.getName());
            }
            if (newFilm.getDescription().length() <= 200) {
                oldFilm.setDescription(newFilm.getDescription());

            }
            if (newFilm.getReleaseDate().isAfter(LocalDate.of(1895, 12, 28))) {
                oldFilm.setReleaseDate(newFilm.getReleaseDate());
            }

            if (newFilm.getDuration() > 0) {
                oldFilm.setDuration(newFilm.getDuration());

            }
            log.info("Film with id = {} was updated", newFilm.getId());
            return oldFilm;
        } else {
            throw new NotFoundException("Film с id = " + newFilm.getId() + " не найден");
        }
    }

    @Override
    public Film findFilmById(long filmId) {
            if (films.containsKey(filmId)) {
                return films.get(filmId);
            }
        throw new NotFoundException("Film с id = " + filmId + " не найден");
    }

    @Override
    public Collection<Film> getAllFilms() {
        return films.values();
    }

    @Override
    public Collection<Film> findFilmsByName(String name) {
        Collection<Film> matchingFilms = new ArrayList<>();
        for (Film film : films.values()) {
            if (film.getName().equalsIgnoreCase(name)) {
                matchingFilms.add(film);
            }
        }
        return matchingFilms;
    }

    @Override
    public Collection<Film> findFilmsByYear(int year) {
        Collection<Film> matchingFilms = new ArrayList<>();
        for (Film film : films.values()) {
            if (film.getReleaseDate().getYear() == year) {
                matchingFilms.add(film);
            }
        }
        return matchingFilms;
    }

    @Override
    public int countFilms() {
        return films.size();
    }

    // вспомогательный метод для генерации идентификатора нового поста
    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}
