package com.dao;

import com.domain.Hall;

import java.util.List;

public interface HallDao {

    Hall create(Hall hall) throws DaoException;

    void update(Hall hall) throws DaoException;

    void delete(Hall hall) throws DaoException;

    List<Hall> getHallAll() throws DaoException;

    Hall getHallById(int id) throws DaoException;
}