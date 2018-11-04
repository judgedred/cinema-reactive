package com.dao;

import com.domain.Hall;
import com.domain.Seat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.List;

public interface SeatRepository extends MongoRepository<Seat, BigInteger> {

    List<Seat> findAllByHall(Hall hall);

    List<Seat> findByHallAndSeatIdNotIn(Hall hall, List<BigInteger> seatIds);
}