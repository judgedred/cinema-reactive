package com.dao;

import com.domain.Filmshow;
import com.domain.Seat;
import com.domain.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.List;

public interface TicketRepository extends MongoRepository<Ticket, BigInteger> {

    List<Ticket> findAllByFilmshow(Filmshow filmshow);

    List<Ticket> findAllBySeat(Seat seat);

    List<Ticket> findByFilmshowAndTicketIdNotIn(Filmshow filmshow, List<BigInteger> ticketIds);

}
