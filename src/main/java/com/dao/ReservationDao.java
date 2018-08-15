package com.dao;

import com.domain.Reservation;
import com.domain.Ticket;
import com.domain.User;

import java.util.List;

public interface ReservationDao {

    Reservation create(Reservation reservation) throws DaoException;

    void update(Reservation reservation) throws DaoException;

    void delete(Reservation reservation) throws DaoException;

    List<Reservation> getReservationAll() throws DaoException;

    Reservation getReservationById(int id) throws DaoException;

    List<Reservation> getReservationAllByUser(User user) throws DaoException;

    List<Reservation> getReservationAllByTicket(Ticket ticket) throws DaoException;

}