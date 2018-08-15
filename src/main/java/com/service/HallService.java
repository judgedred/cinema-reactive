package com.service;

import com.dao.DaoException;
import com.domain.Hall;

import java.util.List;

public interface HallService {

    Hall create(Hall hall) throws DaoException;

    void update(Hall hall) throws DaoException;

    void delete(Hall hall) throws DaoException;

    List<Hall> getHallAll() throws DaoException;

    Hall getHallById(int id) throws DaoException;

    boolean checkHallInFilmshow(Hall hall) throws DaoException;

    boolean checkHallInSeat(Hall hall) throws DaoException;
}
