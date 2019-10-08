package com.dao;

import com.domain.Seat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

public interface SeatBlockingRepository extends MongoRepository<Seat, BigInteger> {

}