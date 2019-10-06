package com.dao;

import com.domain.Hall;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface HallRepository extends ReactiveMongoRepository<Hall, BigInteger> {

    Mono<Hall> findByHallNumber(Integer hallNumber);

}