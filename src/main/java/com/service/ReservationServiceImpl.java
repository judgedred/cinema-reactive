package com.service;

import com.dao.DaoException;
import com.dao.ReservationDao;
import com.domain.Reservation;
import com.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationDao reservationDao;

    @Override
    public Reservation create(Reservation reservation) throws DaoException {
        return reservationDao.create(reservation);
    }

    @Override
    public void update(Reservation reservation) throws DaoException {
        reservationDao.update(reservation);
    }

    @Override
    public void delete(Reservation reservation) throws DaoException {
        reservationDao.delete(reservation);
    }

    @Override
    public List<Reservation> getReservationAll() throws DaoException {
        return reservationDao.getReservationAll();
    }

    @Override
    public Reservation getReservationById(int id) throws DaoException {
        return reservationDao.getReservationById(id);
    }

    @Override
    public List<Reservation> getReservationAllByUser(User user) throws DaoException {
        return reservationDao.getReservationAllByUser(user);
    }

}
