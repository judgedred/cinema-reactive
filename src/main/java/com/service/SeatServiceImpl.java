package com.service;


import com.dao.DaoException;
import com.dao.SeatDao;
import com.domain.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatServiceImpl implements SeatService
{
    @Autowired
    private SeatDao seatDao;

    @Override
    public Seat create(Seat seat) throws DaoException
    {
        return seatDao.create(seat);
    }

    @Override
    public void update(Seat seat) throws DaoException
    {
        seatDao.update(seat);
    }

    @Override
    public void delete(Seat seat) throws DaoException
    {
        seatDao.delete(seat);
    }

    @Override
    public List<Seat> getSeatAll() throws DaoException
    {
        return seatDao.getSeatAll();
    }

    @Override
    public Seat getSeatById(int id) throws DaoException
    {
        return seatDao.getSeatById(id);
    }
}
