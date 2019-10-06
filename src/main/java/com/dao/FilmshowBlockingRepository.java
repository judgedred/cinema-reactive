package com.dao;

import com.domain.Filmshow;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

public interface FilmshowBlockingRepository extends MongoRepository<Filmshow, BigInteger> {

}