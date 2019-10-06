package com.service;

import com.dao.TicketRepository;
import com.domain.Filmshow;
import com.domain.Reservation;
import com.domain.Seat;
import com.domain.Ticket;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultTicketService implements TicketService {

    private final TicketRepository ticketRepository;
    private final ReservationService reservationService;

    public DefaultTicketService(TicketRepository ticketRepository, ReservationService reservationService) {
        this.ticketRepository = ticketRepository;
        this.reservationService = reservationService;
    }

    @Override
    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public void delete(Ticket ticket) {
        ticketRepository.delete(ticket);
    }

    @Override
    public List<Ticket> getTicketAll() {
        return ticketRepository.findAll();
    }

    @Override
    public Optional<Ticket> getTicketById(BigInteger id) {
        return ticketRepository.findById(id);
    }

    @Override
    public List<Ticket> getTicketAllByFilmshow(Filmshow filmshow) {
        return ticketRepository.findAllByFilmshow(filmshow);
    }

    @Override
    public List<Ticket> getTicketAllBySeat(Seat seat) {
        return ticketRepository.findAllBySeat(seat);
    }

    @Override
    public Flux<Ticket> getTicketFreeByFilmshow(Filmshow filmshow) {
        return reservationService.getReservationAll()
                .map(Reservation::getTicket)
                .map(Ticket::getTicketId)
                .collect(Collectors.toList())
                .flatMapIterable(ticketIds -> ticketRepository.findByFilmshowAndTicketIdNotIn(filmshow, ticketIds));
    }
}
