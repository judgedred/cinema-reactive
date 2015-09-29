package com.service;


import com.dao.DaoException;
import com.domain.Hall;

import java.util.List;

public interface HallService
{
    public Hall create(Hall hall) throws DaoException;
    public void update(Hall hall) throws DaoException;
    public void delete(Hall hall) throws DaoException;
    public List<Hall> getHallAll() throws DaoException;
    public Hall getHallById(int id) throws DaoException;
}
