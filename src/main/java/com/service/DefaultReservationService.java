package com.service;

import com.dao.ReservationRepository;
import com.domain.Reservation;
import com.domain.Ticket;
import com.domain.User;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultReservationService implements ReservationService {

    private final ReservationRepository reservationRepository;

    public DefaultReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation create(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public void delete(Reservation reservation) {
        reservationRepository.delete(reservation);
    }

    @Override
    public List<Reservation> getReservationAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Optional<Reservation> getReservationById(BigInteger id) {
        return reservationRepository.findById(id);
    }

    @Override
    public List<Reservation> getReservationAllByUser(User user) {
        return reservationRepository.findAllByUser(user);
    }

    @Override
    public List<Reservation> getReservationAllByTicket(Ticket ticket) {
        return reservationRepository.findAllByTicket(ticket);
    }

}
