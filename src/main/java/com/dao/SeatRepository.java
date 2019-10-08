package com.dao;

import com.domain.Hall;
import com.domain.Seat;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.math.BigInteger;
import java.util.List;

public interface SeatRepository extends ReactiveMongoRepository<Seat, BigInteger> {

    Flux<Seat> findAllByHall(Hall hall);

    Flux<Seat> findByHallAndSeatIdNotInOrderBySeatNumberAscRowNumberAsc(Hall hall, List<BigInteger> seatIds);
}