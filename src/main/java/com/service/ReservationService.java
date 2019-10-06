package com.service;

import com.domain.Reservation;
import com.domain.Ticket;
import com.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface ReservationService {

    Mono<Reservation> save(Reservation reservation);

    Mono<Void> delete(Reservation reservation);

    Flux<Reservation> getReservationAll();

    Mono<Reservation> getReservationById(BigInteger id);

    Flux<Reservation> getReservationAllByUser(User user);

    Flux<Reservation> getReservationAllByTicket(Ticket ticket);
}
