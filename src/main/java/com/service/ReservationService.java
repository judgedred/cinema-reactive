package com.service;

import com.domain.Reservation;
import com.domain.Ticket;
import com.domain.User;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface ReservationService {

    Reservation create(Reservation reservation);

    void delete(Reservation reservation);

    List<Reservation> getReservationAll();

    Optional<Reservation> getReservationById(BigInteger id);

    List<Reservation> getReservationAllByUser(User user);

    List<Reservation> getReservationAllByTicket(Ticket ticket);
}
