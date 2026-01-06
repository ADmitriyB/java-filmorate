package ru.yandex.practicum.filmorate.dal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.dal.mappers.MpaRowMapper;
import ru.yandex.practicum.filmorate.dto.NewFilmRequest;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;


@Slf4j
@Repository
public class FilmRepository extends BaseRepository<Film> {

    private static final String FIND_ALL_QUERY = "SELECT * FROM films";
    private static final String INSERT_QUERY = "INSERT INTO films(name, description, duration, releaseDate, mpaId, genreId) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM films WHERE id = ?";
    private static final String FIND_BY_MPA_ID_QUERY = "SELECT * FROM films where rating_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM films WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE films SET name = ?, description = ? WHERE id = ?";

    public FilmRepository(JdbcTemplate jdbc, FilmRowMapper mapper) {
        super(jdbc, mapper, Film.class);
    }

    public List<Film> findAll() {
        return findMany(FIND_ALL_QUERY);
    }

    @Autowired
    MpaRepository mpaRepository;

    public boolean  mpaIdExists(Integer mpaId) {
        try {
            return mpaRepository.findById(mpaId).isPresent();
        } catch (EmptyResultDataAccessException e) {
            // Если запись не найдена, значит mpaId не существует
            return false;
        }
    }

    @Autowired
    GenreRepository genreRepository;

    public boolean  genreIdExists(Integer genreId) {
        try {
            return genreRepository.findById(genreId).isPresent();
        } catch (EmptyResultDataAccessException e) {

            return false;
        }
    }

    public Film save(Film film) {


        if (film.getMpaId() != null) {
            if (!mpaIdExists(film.getMpaId())) {
                throw new NotFoundException("Mpa not found");
            }
        }

            if (film.getGenreId() != null) {
                if (!genreIdExists(film.getGenreId())) {
                    throw new NotFoundException("Genre not found");

            }
        }

        log.debug("Save film with parameters: id={}, name={}, description={}, duration={}, releaseDate={}, mpaId={}, genreId={}",
                film.getId(), film.getName(), film.getDescription(), film.getDuration(), film.getReleaseDate(),
                film.getMpaId(), film.getGenreId());


        Integer id = insert(
                INSERT_QUERY,
                film.getName(),
                film.getDescription(),
                film.getDuration(),
                film.getReleaseDate(),
                film.getMpaId(),
                film.getGenreId()
        );
        film.setId(id);
        return film;
    }

    public Optional<Film> findById(Integer filmId) {
        return findOne(FIND_BY_ID_QUERY, filmId);
    }

    public List<Film> findByMpaId(Integer mpaId) {
        return findMany(FIND_BY_MPA_ID_QUERY, mpaId);
    }

    public boolean delete(Integer imageId) {
        return delete(DELETE_QUERY, imageId);
    }

    public Film update(Film film) {
        update(
                UPDATE_QUERY,
                film.getId(),
                film.getName(),
                film.getDescription()
        );
        return film;
    }
}
