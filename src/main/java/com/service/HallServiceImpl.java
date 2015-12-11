package com.service;


import com.dao.DaoException;
import com.dao.FilmshowDao;
import com.dao.HallDao;
import com.dao.SeatDao;
import com.domain.Filmshow;
import com.domain.Hall;
import com.domain.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HallServiceImpl implements HallService
{
    @Autowired
    private HallDao hallDao;

    @Autowired
    private FilmshowDao filmshowDao;

    @Autowired
    private SeatDao seatDao;


    @Override
    public Hall create(Hall hall) throws DaoException
    {
        return hallDao.create(hall);
    }

    @Override
    public void update(Hall hall) throws DaoException
    {
        hallDao.update(hall);
    }

    @Override
    public void delete(Hall hall) throws DaoException
    {
        hallDao.delete(hall);
    }

    @Override
    public List<Hall> getHallAll() throws DaoException
    {
        return hallDao.getHallAll();
    }

    @Override
    public Hall getHallById(int id) throws DaoException
    {
        return hallDao.getHallById(id);
    }

    @Override
    public boolean checkHallInFilmshow(Hall hall) throws DaoException
    {
        List<Filmshow> filmshowList = filmshowDao.getFilmshowByHall(hall);
        if(filmshowList != null)
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkHallInSeat(Hall hall) throws DaoException
    {
        List<Seat> seatList = seatDao.getSeatByHall(hall);
        if(seatList != null)
        {
            return true;
        }
        return false;
    }
}
