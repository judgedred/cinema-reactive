package com.service;

import com.dao.ReservationRepository;
import com.domain.Reservation;
import com.domain.Ticket;
import com.domain.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Service
public class DefaultReservationService implements ReservationService {

    private final ReservationRepository reservationRepository;

    public DefaultReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Mono<Reservation> save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Mono<Void> delete(Reservation reservation) {
        return reservationRepository.delete(reservation);
    }

    @Override
    public Flux<Reservation> getReservationAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Mono<Reservation> getReservationById(BigInteger id) {
        return reservationRepository.findById(id);
    }

    @Override
    public Flux<Reservation> getReservationAllByUser(User user) {
        return reservationRepository.findAllByUser(user);
    }

    @Override
    public Flux<Reservation> getReservationAllByTicket(Ticket ticket) {
        return reservationRepository.findAllByTicket(ticket);
    }

}
