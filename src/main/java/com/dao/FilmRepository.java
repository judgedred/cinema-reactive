package com.dao;

import com.domain.Film;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

public interface FilmRepository extends MongoRepository<Film, Integer> {

    Film getFilmByfilmId(BigInteger id);
}
