package com.dao;

import com.domain.Filmshow;
import com.domain.Seat;
import com.domain.Ticket;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.math.BigInteger;
import java.util.List;

public interface TicketRepository extends ReactiveMongoRepository<Ticket, BigInteger> {

    Flux<Ticket> findAllByFilmshow(Filmshow filmshow);

    Flux<Ticket> findAllBySeat(Seat seat);

    Flux<Ticket> findByFilmshowAndTicketIdNotIn(Filmshow filmshow, List<BigInteger> ticketIds);

}
