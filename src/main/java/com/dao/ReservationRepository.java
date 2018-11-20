package com.dao;

import com.domain.Reservation;
import com.domain.Ticket;
import com.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.List;

public interface ReservationRepository extends MongoRepository<Reservation, BigInteger> {

    List<Reservation> findAllByUser(User user);

    List<Reservation> findAllByTicket(Ticket ticket);

}