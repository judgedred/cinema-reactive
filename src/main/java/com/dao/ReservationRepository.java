package com.dao;

import com.domain.Reservation;
import com.domain.Ticket;
import com.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.math.BigInteger;

public interface ReservationRepository extends ReactiveMongoRepository<Reservation, BigInteger> {

    Flux<Reservation> findAllByUser(User user);

    Flux<Reservation> findAllByTicket(Ticket ticket);

}