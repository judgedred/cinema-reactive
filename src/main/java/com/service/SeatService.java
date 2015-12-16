package com.service;


import com.dao.DaoException;
import com.domain.Filmshow;
import com.domain.Seat;
import java.util.List;

public interface SeatService
{
    public Seat create(Seat seat) throws DaoException;
    public void update(Seat seat) throws DaoException;
    public void delete(Seat seat) throws DaoException;
    public List<Seat> getSeatAll() throws DaoException;
    public Seat getSeatById(int id) throws DaoException;
    public boolean checkSeatInTicket(Seat seat) throws DaoException;
    public List<Seat> getSeatFreeByFilmshow(Filmshow filmshow)throws DaoException;
}
