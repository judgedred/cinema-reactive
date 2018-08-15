package com.dao;

import com.domain.Filmshow;
import com.domain.Hall;
import com.domain.Seat;

import java.util.List;

public interface SeatDao {

    Seat create(Seat seat) throws DaoException;

    void update(Seat seat) throws DaoException;

    void delete(Seat seat) throws DaoException;

    List<Seat> getSeatAll() throws DaoException;

    Seat getSeatById(int id) throws DaoException;

    List<Seat> getSeatAllByHall(Hall hall) throws DaoException;

    List<Seat> getSeatFreeByFilmshow(Filmshow filmshow) throws DaoException;
}