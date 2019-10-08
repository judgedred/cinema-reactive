package com.service;

import com.dao.SeatRepository;
import com.domain.Filmshow;
import com.domain.Hall;
import com.domain.Seat;
import com.domain.Ticket;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.stream.Collectors;

@Service
public class DefaultSeatService implements SeatService {

    private final SeatRepository seatRepository;
    private final TicketService ticketService;

    public DefaultSeatService(SeatRepository seatRepository, TicketService ticketService) {
        this.seatRepository = seatRepository;
        this.ticketService = ticketService;
    }

    @Override
    public Mono<Seat> save(Seat seat) {
        return seatRepository.save(seat);
    }

    @Override
    public Mono<Void> delete(Seat seat) {
        return seatRepository.delete(seat);
    }

    @Override
    public Flux<Seat> getSeatAll() {
        return seatRepository.findAll();
    }

    @Override
    public Mono<Seat> getSeatById(BigInteger id) {
        return seatRepository.findById(id);
    }

    @Override
    public Flux<Seat> getSeatFreeByFilmshow(Filmshow filmshow) {
        return ticketService.getTicketAllByFilmshow(filmshow)
                .map(Ticket::getSeat)
                .map(Seat::getSeatId)
                .collect(Collectors.toList())
                .flatMapMany(seatIds -> seatRepository.findByHallAndSeatIdNotInOrderBySeatNumberAscRowNumberAsc(
                        filmshow.getHall(), seatIds));
    }

    @Override
    public Flux<Seat> getSeatAllByHall(Hall hall) {
        return seatRepository.findAllByHall(hall);
    }
}
