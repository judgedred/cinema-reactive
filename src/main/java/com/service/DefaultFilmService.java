package com.service;

import com.dao.FilmRepository;
import com.dao.FilmshowRepository;
import com.domain.Film;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultFilmService implements FilmService {

    private final FilmRepository filmRepository;
    private final FilmshowRepository filmshowRepository;

    public DefaultFilmService(FilmRepository filmRepository, FilmshowRepository filmshowRepository) {
        this.filmRepository = filmRepository;
        this.filmshowRepository = filmshowRepository;
    }

    @Override
    public Film create(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public void update(Film film) {
        filmRepository.save(film);
    }

    @Override
    public void delete(Film film) {
        filmRepository.delete(film);
    }

    @Override
    public List<Film> getFilmAll() {
        return filmRepository.findAll();
    }

    @Override
    public Optional<Film> getFilmById(BigInteger id) {
        return filmRepository.findById(id);
    }

    @Override
    public boolean checkFilmInFilmshow(Film film) {
        return !filmshowRepository.findAllByFilm(film).isEmpty();
    }
}
