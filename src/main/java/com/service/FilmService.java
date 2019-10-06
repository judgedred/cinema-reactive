package com.service;

import com.domain.Film;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface FilmService {

    Mono<Film> save(Film film);

    Mono<Void> delete(Film film);

    Flux<Film> getFilmAll();

    Mono<Film> getFilmById(BigInteger id);

    Mono<Film> getFilmByName(String filmName);

}
