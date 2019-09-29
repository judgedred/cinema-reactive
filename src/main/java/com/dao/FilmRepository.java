package com.dao;

import com.domain.Film;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface FilmRepository extends ReactiveMongoRepository<Film, BigInteger> {

    Mono<Film> findByFilmName(String filmName);

}
