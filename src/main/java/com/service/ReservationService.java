package com.service;


import com.dao.DaoException;
import com.domain.Reservation;
import com.domain.Ticket;
import com.domain.User;

import java.util.List;

public interface ReservationService
{
    public Reservation create(Reservation reservation) throws DaoException;
    public void update(Reservation reservation) throws DaoException;
    public void delete(Reservation reservation) throws DaoException;
    public List<Reservation> getReservationAll() throws DaoException;
    public Reservation getReservationById(int id) throws DaoException;
    public List<Reservation> getReservationAllByUser(User user) throws DaoException;
}
