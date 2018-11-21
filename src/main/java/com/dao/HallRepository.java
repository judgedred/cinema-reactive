package com.dao;

import com.domain.Hall;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface HallRepository extends MongoRepository<Hall, BigInteger> {

    Optional<Hall> findByHallNumber(Integer hallNumber);

}