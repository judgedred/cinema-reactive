package com.dao;

import com.domain.Film;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

public interface FilmBlockingRepository extends MongoRepository<Film, BigInteger> {

}
