package com.mysql;

import com.dao.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public class MySqlDaoFactory implements DaoFactory
{
	public static SessionFactory createSessionFactory()
	{
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		return configuration.buildSessionFactory(serviceRegistry);
	}
	
	@Override
	public UserDao getUserDao() throws DaoException
	{
		return new MySqlUserDao();
	}

	@Override
	public FilmDao getFilmDao() throws DaoException
	{
		return new MySqlFilmDao();
	}

	@Override
	public HallDao getHallDao() throws DaoException
	{
		return new MySqlHallDao();
	}

	@Override
	public SeatDao getSeatDao() throws DaoException
	{
		return new MySqlSeatDao();
	}

	@Override
	public TicketDao getTicketDao() throws DaoException
	{
		return new MySqlTicketDao();
	}

	@Override
	public FilmshowDao getFilmshowDao() throws DaoException
	{
		return new MySqlFilmshowDao();
	}

	@Override
	public ReservationDao getReservationDao() throws DaoException
	{
		return new MySqlReservationDao();
	}

	public MySqlDaoFactory()
	{

	}
}