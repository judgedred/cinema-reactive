package com.service;

import com.dao.SeatRepository;
import com.domain.Filmshow;
import com.domain.Hall;
import com.domain.Seat;
import com.domain.Ticket;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
    public Seat save(Seat seat) {
        return seatRepository.save(seat);
    }

    @Override
    public void delete(Seat seat) {
        seatRepository.delete(seat);
    }

    @Override
    public List<Seat> getSeatAll() {
        return seatRepository.findAll();
    }

    @Override
    public Optional<Seat> getSeatById(BigInteger id) {
        return seatRepository.findById(id);
    }

    @Override
    public boolean checkSeatInTicket(Seat seat) {
        return !ticketService.getTicketAllBySeat(seat).isEmpty();
    }

    @Override
    public List<Seat> getSeatFreeByFilmshow(Filmshow filmshow) {
        return Optional.of(ticketService.getTicketAllByFilmshow(filmshow))
                .map(tickets -> tickets.stream()
                        .map(Ticket::getSeat)
                        .map(Seat::getSeatId)
                        .collect(Collectors.toList()))
                .map(seatIds -> seatRepository.findByHallAndSeatIdNotIn(filmshow.getHall(), seatIds))
                .orElse(Collections.emptyList());
    }

    @Override
    public List<Seat> getSeatAllByHall(Hall hall) {
        return seatRepository.findAllByHall(hall);
    }
}
