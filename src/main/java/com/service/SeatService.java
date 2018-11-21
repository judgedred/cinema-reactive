package com.service;

import com.domain.Filmshow;
import com.domain.Hall;
import com.domain.Seat;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface SeatService {

    Seat save(Seat seat);

    void delete(Seat seat);

    List<Seat> getSeatAll();

    Optional<Seat> getSeatById(BigInteger id);

    boolean checkSeatInTicket(Seat seat);

    List<Seat> getSeatFreeByFilmshow(Filmshow filmshow);

    List<Seat> getSeatAllByHall(Hall hall);
}
