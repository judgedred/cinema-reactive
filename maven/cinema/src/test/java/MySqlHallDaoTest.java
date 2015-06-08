package test;

import com.dao.*;
import com.domain.*;
import com.mysql.*;
import java.util.*;
import org.junit.Test;
import org.junit.Assert;


public class MySqlHallDaoTest
{
	@Test
	public void testGetHallById() throws DaoException
	{
		DaoFactory daoFactory = new MySqlDaoFactory();
		HallDao hallDao = daoFactory.getHallDao();
		Hall hallTest = hallDao.getHallById(1);
		Assert.assertNotNull(hallTest);
	}	

	@Test
	public void testCreate() throws DaoException
	{
		DaoFactory daoFactory = new MySqlDaoFactory();
		HallDao hallDao = daoFactory.getHallDao();
		Hall hall = new Hall();
		hall.setHallNumber(4);
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
	public void testUpdate() throws DaoException
	{
		DaoFactory daoFactory = new MySqlDaoFactory();
		HallDao hallDao = daoFactory.getHallDao();
		Hall hall = new Hall();
		hall.setHallId(2);
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
	public void testDelete() throws DaoException
	{
		DaoFactory daoFactory = new MySqlDaoFactory();
		HallDao hallDao = daoFactory.getHallDao();
		Hall hall = new Hall();
		hall.setHallId(3);
		hallDao.delete(hall);
		Assert.assertNull(hallDao.getHallById(hall.getHallId()));
	}

	@Test
	public void testGetHallAll() throws DaoException
	{
		DaoFactory daoFactory = new MySqlDaoFactory();
		HallDao hallDao = daoFactory.getHallDao();
		List<Hall> listTest = hallDao.getHallAll();
		Assert.assertNotNull(listTest);
		Assert.assertTrue(listTest.size() > 0);
	}
}
