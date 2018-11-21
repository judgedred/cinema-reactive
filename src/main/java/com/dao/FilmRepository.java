package com.dao;

import com.domain.Film;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface FilmRepository extends MongoRepository<Film, BigInteger> {

    Optional<Film> findByFilmName(String filmName);

}
