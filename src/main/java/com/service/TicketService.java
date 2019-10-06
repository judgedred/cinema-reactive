package com.service;

import com.domain.Filmshow;
import com.domain.Seat;
import com.domain.Ticket;
import reactor.core.publisher.Flux;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface TicketService {

    Ticket save(Ticket ticket);

    void delete(Ticket ticket);

    List<Ticket> getTicketAll();

    Optional<Ticket> getTicketById(BigInteger id);

    Flux<Ticket> getTicketFreeByFilmshow(Filmshow filmshow);

    List<Ticket> getTicketAllByFilmshow(Filmshow filmshow);

    List<Ticket> getTicketAllBySeat(Seat seat);

}
