package com.service;

import com.dao.FilmshowRepository;
import com.dao.FilmshowTodayRepository;
import com.domain.Film;
import com.domain.Filmshow;
import com.domain.FilmshowToday;
import com.domain.Hall;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class DefaultFilmshowService implements FilmshowService {

    private final FilmshowRepository filmshowRepository;
    private final FilmshowTodayRepository filmshowTodayRepository;

    public DefaultFilmshowService(FilmshowRepository filmshowRepository, FilmshowTodayRepository filmshowTodayRepository) {
        this.filmshowRepository = filmshowRepository;
        this.filmshowTodayRepository = filmshowTodayRepository;
    }

    @Override
    public Mono<Filmshow> save(Filmshow filmshow) {
        return filmshowRepository.save(filmshow);
    }

    @Override
    public Mono<Void> refreshFilmshowToday(FilmshowToday filmshowToday) {
        return filmshowTodayRepository.insert(filmshowToday)
                .then();
    }

    @Override
    public Mono<Void> refreshFilmshowToday() {
        return getFilmshowToday()
                .collectList()
                .map(FilmshowToday::new)
                .flatMap(this::refreshFilmshowToday);
    }

    @Override
    public Mono<Void> delete(Filmshow filmshow) {
        return filmshowRepository.delete(filmshow);
    }

    @Override
    public Flux<Filmshow> getFilmshowAll() {
        return filmshowRepository.findAll();
    }

    @Override
    public Mono<Filmshow> getFilmshowById(BigInteger id) {
        return filmshowRepository.findById(id);
    }

    @Override
    public Flux<Filmshow> getFilmshowToday() {
        LocalDateTime startDate = LocalDateTime.now().plusMinutes(30);
        LocalDateTime endDate = LocalDateTime.of(startDate.toLocalDate(), LocalTime.of(23, 59));
        return filmshowRepository.findByDateTimeBetween(startDate, endDate);
    }

    @Override
    public Flux<FilmshowToday> getFilmshowTodayLive() {
        return filmshowTodayRepository.findFilmshowTodayBy();
    }

    @Override
    public Flux<Filmshow> getFilmshowWeek() {
        LocalDateTime startDate = LocalDateTime.now().plusMinutes(30);
        LocalDateTime endDate = startDate.plusDays(7);
        return filmshowRepository.findByDateTimeBetween(startDate, endDate);
    }

    @Override
    public Flux<Filmshow> getFilmshowByFilm(Film film) {
        return filmshowRepository.findAllByFilm(film);
    }

    @Override
    public Flux<Filmshow> getFilmshowByHall(Hall hall) {
        return filmshowRepository.findAllByHall(hall);
    }

}
