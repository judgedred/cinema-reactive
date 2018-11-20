package com.service;

import com.dao.DaoException;
import com.domain.Hall;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface HallService {

    Hall save(Hall hall) throws DaoException;

    void delete(Hall hall) throws DaoException;

    List<Hall> getHallAll() throws DaoException;

    Optional<Hall> getHallById(BigInteger id) throws DaoException;

    boolean checkHallInFilmshow(Hall hall) throws DaoException;

    boolean checkHallInSeat(Hall hall) throws DaoException;
}
