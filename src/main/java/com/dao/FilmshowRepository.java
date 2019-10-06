package com.dao;

import com.domain.Film;
import com.domain.Filmshow;
import com.domain.Hall;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.math.BigInteger;
import java.time.LocalDateTime;

public interface FilmshowRepository extends ReactiveMongoRepository<Filmshow, BigInteger> {

    Flux<Filmshow> findAllByFilm(Film film);

    Flux<Filmshow> findAllByHall(Hall hall);

    Flux<Filmshow> findByDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);

}