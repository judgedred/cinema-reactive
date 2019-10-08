package com.service;

import com.domain.Filmshow;
import com.domain.Seat;
import com.domain.Ticket;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface TicketService {

    Mono<Ticket> save(Ticket ticket);

    Mono<Void> delete(Ticket ticket);

    Flux<Ticket> getTicketAll();

    Mono<Ticket> getTicketById(BigInteger id);

    Flux<Ticket> getTicketFreeByFilmshow(Filmshow filmshow);

    Flux<Ticket> getTicketAllByFilmshow(Filmshow filmshow);

    Flux<Ticket> getTicketAllBySeat(Seat seat);

}
