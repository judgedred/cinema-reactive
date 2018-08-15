package com.service;

import com.dao.DaoException;
import com.domain.Reservation;
import com.domain.User;

import java.util.List;

public interface ReservationService {

    Reservation create(Reservation reservation) throws DaoException;

    void update(Reservation reservation) throws DaoException;

    void delete(Reservation reservation) throws DaoException;

    List<Reservation> getReservationAll() throws DaoException;

    Reservation getReservationById(int id) throws DaoException;

    List<Reservation> getReservationAllByUser(User user) throws DaoException;
}
