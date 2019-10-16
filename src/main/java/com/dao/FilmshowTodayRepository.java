package com.dao;

import com.domain.FilmshowToday;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

import java.math.BigInteger;

public interface FilmshowTodayRepository extends ReactiveMongoRepository<FilmshowToday, BigInteger> {

    @Tailable
    Flux<FilmshowToday> findFilmshowTodayBy();

}