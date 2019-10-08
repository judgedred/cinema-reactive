package com.service;

import com.domain.Filmshow;
import com.domain.Hall;
import com.domain.Seat;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface SeatService {

    Mono<Seat> save(Seat seat);

    Mono<Void> delete(Seat seat);

    Flux<Seat> getSeatAll();

    Mono<Seat> getSeatById(BigInteger id);

    Flux<Seat> getSeatFreeByFilmshow(Filmshow filmshow);

    Flux<Seat> getSeatAllByHall(Hall hall);
}
