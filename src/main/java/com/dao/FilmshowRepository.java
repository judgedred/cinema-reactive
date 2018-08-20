package com.dao;

import com.domain.Film;
import com.domain.Filmshow;
import com.domain.Hall;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public interface FilmshowRepository extends MongoRepository<Filmshow, BigInteger> {

    List<Filmshow> findAllByFilm(Film film);

    List<Filmshow> findAllByHall(Hall hall);

    List<Filmshow> findByDateTimeBetween(Date startDate, Date endDate);

}