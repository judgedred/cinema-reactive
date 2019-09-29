package com.service;

import com.dao.FilmRepository;
import com.domain.Film;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Service
public class DefaultFilmService implements FilmService {

    private final FilmRepository filmRepository;
    private final FilmshowService filmshowService;

    public DefaultFilmService(FilmRepository filmRepository, FilmshowService filmshowService) {
        this.filmRepository = filmRepository;
        this.filmshowService = filmshowService;
    }

    @Override
    public Mono<Film> save(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public Mono<Void> delete(Film film) {
        return filmRepository.delete(film);
    }

    @Override
    public Flux<Film> getFilmAll() {
        return filmRepository.findAll();
    }

    @Override
    public Mono<Film> getFilmById(BigInteger id) {
        return filmRepository.findById(id);
    }

    @Override
    public Mono<Film> getFilmByName(String filmName) {
        return filmRepository.findByFilmName(filmName);
    }

    @Override
    public boolean checkFilmInFilmshow(Film film) {
        return !filmshowService.getFilmshowByFilm(film).isEmpty();
    }
}
