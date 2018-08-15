package com.dao;

import com.domain.Hall;
import com.mysql.MySqlHallDao;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MySqlHallDaoTest {

    @Autowired
    private MySqlHallDao hallDao;

    @Test
    public void testGetHallById() throws DaoException {
        Hall hallTest = hallDao.getHallById(1);
        Assert.assertNotNull(hallTest);
    }

    @Test
    public void testCreate() throws DaoException {
        Hall hall = new Hall();
        hall.setHallNumber(5);
        hall.setHallName("testCreatePassed");
        int numberExpected = hall.getHallNumber();
        String hallNameExpected = hall.getHallName();
        Hall hallTest = hallDao.create(hall);
        Assert.assertNotNull(hallTest);
        int numberResult = hallTest.getHallNumber();
        String hallNameResult = hallTest.getHallName();
        Assert.assertEquals(numberExpected, numberResult);
        Assert.assertEquals(hallNameExpected, hallNameResult);
    }

    @Test
    public void testUpdate() throws DaoException {
        Hall hall = new Hall();
        hall.setHallId(3);
        hall.setHallNumber(777);
        hall.setHallName("testUpdatePassed");
        int numberExpected = hall.getHallNumber();
        String hallNameExpected = hall.getHallName();
        hallDao.update(hall);
        Hall hallTest = hallDao.getHallById(hall.getHallId());
        Assert.assertNotNull(hallTest);
        int numberResult = hallTest.getHallNumber();
        String hallNameResult = hallTest.getHallName();
        Assert.assertEquals(numberExpected, numberResult);
        Assert.assertEquals(hallNameExpected, hallNameResult);
    }

    @Test
    public void testDelete() throws DaoException {
        Hall hall = hallDao.getHallById(4);
        hallDao.delete(hall);
        Assert.assertNull(hallDao.getHallById(hall.getHallId()));
    }

    @Test
    public void testGetHallAll() throws DaoException {
        List<Hall> listTest = hallDao.getHallAll();
        Assert.assertNotNull(listTest);
        Assert.assertTrue(listTest.size() > 0);
    }

    @After
    public void cleanUp() throws DaoException {
        List<Hall> lst = hallDao.getHallAll();
        for (Hall h : lst) {
            if (h.getHallName().equals("testCreatePassed") || h.getHallName().equals("testUpdatePassed")) {
                hallDao.delete(h);
            }
        }
    }
}
