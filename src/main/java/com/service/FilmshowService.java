package com.service;

import com.domain.Film;
import com.domain.Filmshow;
import com.domain.Hall;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface FilmshowService {

    Mono<Filmshow> save(Filmshow filmshow);

    Mono<Void> delete(Filmshow filmshow);

    Flux<Filmshow> getFilmshowAll();

    Mono<Filmshow> getFilmshowById(BigInteger id);

    Flux<Filmshow> getFilmshowToday();

    Flux<Filmshow> getFilmshowWeek();

    Flux<Filmshow> getFilmshowByFilm(Film film);

    Flux<Filmshow> getFilmshowByHall(Hall hall);
}
