package com.service;

import com.dao.DaoException;
import com.domain.Reservation;
import com.domain.User;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface ReservationService {

    Reservation create(Reservation reservation) throws DaoException;

    void delete(Reservation reservation) throws DaoException;

    List<Reservation> getReservationAll() throws DaoException;

    Optional<Reservation> getReservationById(BigInteger id) throws DaoException;

    List<Reservation> getReservationAllByUser(User user) throws DaoException;
}
