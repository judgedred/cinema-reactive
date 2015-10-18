package com.service;


import com.dao.DaoException;
import com.dao.ReservationDao;
import com.domain.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService
{
    @Autowired
    private ReservationDao reservationDao;

    @Override
    public Reservation create(Reservation reservation) throws DaoException
    {
        List<Reservation> reservationList = reservationDao.getReservationAll();
        boolean reservationValid = true;
        for(Reservation r : reservationList)
        {
            if(r.getTicket().equals(reservation.getTicket()))
            {
                reservationValid = false;
                break;
            }
        }
        if(reservationValid)
        {
            return reservationDao.create(reservation);
        }
        else
        {
            return null;
        }
    }

    @Override
    public void update(Reservation reservation) throws DaoException
    {
        reservationDao.update(reservation);
    }

    @Override
    public void delete(Reservation reservation) throws DaoException
    {
        reservationDao.delete(reservation);
    }

    @Override
    public List<Reservation> getReservationAll() throws DaoException
    {
        return reservationDao.getReservationAll();
    }

    @Override
    public Reservation getReservationById(int id) throws DaoException
    {
        return reservationDao.getReservationById(id);
    }
}
