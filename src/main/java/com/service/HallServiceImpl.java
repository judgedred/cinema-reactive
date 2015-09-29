package com.service;


import com.dao.DaoException;
import com.dao.HallDao;
import com.domain.Hall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HallServiceImpl implements HallService
{
    @Autowired
    private HallDao hallDao;

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
}
