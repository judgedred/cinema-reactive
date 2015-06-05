package com.mysql;

import com.dao.*;
import com.domain.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class MySqlReservationDao implements ReservationDao
{
	private Connection connection;
	private PreparedStatement pstmtCreate = null;
	private PreparedStatement pstmtUpdate = null;
	private PreparedStatement pstmtDelete = null;
	private PreparedStatement pstmtGetAll = null;
	public static PreparedStatement pstmtGetById = null;
	private PreparedStatement pstmtLastId = null;
	private ResultSet rs = null;
	private static final String sqlCreate = "Insert Into Reservation(user_id, ticket_id) Values(?, ?)";
	private static final String sqlUpdate = "Update Reservation Set user_id = ?, ticket_id = ? Where reservation_id = ?";
	private static final String sqlDelete = "Delete From Reservation Where reservation_id = ?";
	private static final String sqlGetAll = "Select reservation_id, user_id, ticket_id From Reservation"; 
	private static final String sqlGetById = "Select reservation_id, user_id, ticket_id From Reservation Where reservation_id = ?";
	private static final String sqlLastId = "Select reservation_id, user_id, ticket_id From Reservation Where reservation_id = last_insert_id()";

	private PreparedStatement getPstmtCreate() throws DaoException
	{
		try
		{
			if(pstmtCreate == null)
			{
				return pstmtCreate = connection.prepareStatement(sqlCreate);
			}
			else
			{
				return pstmtCreate;
			}
		}
		catch(Exception e)
		{
			throw new DaoException(e);  
		}
	}

	private PreparedStatement getPstmtUpdate() throws DaoException
	{
		try
		{
			if(pstmtUpdate == null)
			{
				return pstmtUpdate = connection.prepareStatement(sqlUpdate);
			}
			else
			{
				return pstmtUpdate;
			}
		}
		catch(Exception e)
		{
			throw new DaoException(e);  
		}
	}

	private PreparedStatement getPstmtDelete() throws DaoException
	{
		try
		{
			if(pstmtDelete == null)
			{
				return pstmtDelete = connection.prepareStatement(sqlDelete);
			}
			else
			{
				return pstmtDelete;
			}
		}
		catch(Exception e)
		{
			throw new DaoException(e);  
		}
	}

	private PreparedStatement getPstmtGetAll() throws DaoException
	{
		try
		{
			if(pstmtGetAll == null)
			{
				return pstmtGetAll = connection.prepareStatement(sqlGetAll);
			}
			else
			{
				return pstmtGetAll;
			}
		}
		catch(Exception e)
		{
			throw new DaoException(e);  
		}
	}

	private PreparedStatement getPstmtGetById() throws DaoException
	{
		try
		{
			if(pstmtGetById == null)
			{
				return pstmtGetById = connection.prepareStatement(sqlGetById);
			}
			else
			{
				return pstmtGetById;
			}
		}
		catch(Exception e)
		{
			throw new DaoException(e);  
		}
	}

	private PreparedStatement getPstmtLastId() throws DaoException
	{
		try
		{
			if(pstmtLastId == null)
			{
				return pstmtLastId = connection.prepareStatement(sqlLastId);
			}
			else
			{
				return pstmtLastId;
			}
		}
		catch(Exception e)
		{
			throw new DaoException(e);  
		}
	}

	@Override
	public Reservation create(Reservation reservation) throws DaoException
	{
		try
		{
			pstmtCreate = getPstmtCreate();
			pstmtCreate.setInt(1, reservation.getUser().getUserId());
			pstmtCreate.setInt(2, reservation.getTicket().getTicketId());
			pstmtCreate.executeUpdate();
			pstmtLastId = getPstmtLastId();
			rs = pstmtLastId.executeQuery();
			if(rs.next())
			{
				reservation.setReservationId(rs.getInt(1));
				MySqlUserDao userDao = new MySqlUserDao();
				reservation.setUser(userDao.getUserById(rs.getInt(2)));
				MySqlTicketDao ticketDao = new MySqlTicketDao();
				reservation.setTicket(ticketDao.getTicketById(rs.getInt(3)));
			}
		}
		catch(Exception e)
		{
		 	throw new DaoException(e); 
		}
		return reservation;
	}

	@Override
	public void update(Reservation reservation) throws DaoException
	{
		try
		{
			pstmtUpdate = getPstmtUpdate();
			pstmtUpdate.setInt(1, reservation.getUser().getUserId());
			pstmtUpdate.setInt(2, reservation.getTicket().getTicketId());
			pstmtUpdate.setInt(3, reservation.getReservationId());
			pstmtUpdate.executeUpdate();
		}
		catch(Exception e)
		{	
			throw new DaoException(e);
		}
	}

	@Override
	public void delete(Reservation reservation) throws DaoException
	{
		try
		{
			pstmtDelete = getPstmtDelete();
			pstmtDelete.setInt(1, reservation.getReservationId());
			pstmtDelete.executeUpdate();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public List<Reservation> getReservationAll() throws DaoException
	{
		List<Reservation> ls = new ArrayList<>();
		try
		{
			pstmtGetAll = getPstmtGetAll();
			rs = pstmtGetAll.executeQuery();
			MySqlUserDao userDao = new MySqlUserDao();
			MySqlTicketDao ticketDao = new MySqlTicketDao();
			while(rs.next())
			{
				Reservation reservation = new Reservation();
				reservation.setReservationId(rs.getInt(1));
				reservation.setUser(userDao.getUserById(rs.getInt(2)));
				reservation.setTicket(ticketDao.getTicketById(rs.getInt(3)));
				ls.add(reservation);
			}
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
		return ls;
	}

	@Override
	public Reservation getReservationById(int id) throws DaoException
	{
		Reservation reservation = new Reservation();
		try
		{
			pstmtGetById = getPstmtGetById();
			pstmtGetById.setInt(1, id);
			rs = pstmtGetById.executeQuery();
			if(rs.next())
			{
				reservation.setReservationId(rs.getInt(1));
				MySqlUserDao userDao = new MySqlUserDao();
				reservation.setUser(userDao.getUserById(rs.getInt(2)));
				MySqlTicketDao ticketDao = new MySqlTicketDao();
				reservation.setTicket(ticketDao.getTicketById(rs.getInt(3)));
			}
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
		return reservation;
	}

	@Override
	public void close() throws DaoException
	{
		try
		{
			if(pstmtCreate != null)
			{
				pstmtCreate.close();
			}
			if(pstmtUpdate != null)
			{
				pstmtUpdate.close();
			}
			if(pstmtDelete != null)
			{
				pstmtDelete.close();
			}
			if(pstmtGetAll != null)
			{
				pstmtGetAll.close();
			}
			if(pstmtGetById != null)
			{
				pstmtGetById.close();
			}
			if(pstmtLastId != null)
			{
				pstmtLastId.close();
			}
			if(MySqlUserDao.pstmtGetById != null)
			{
				MySqlUserDao.pstmtGetById.close();
			}
			if(MySqlTicketDao.pstmtGetById != null)
			{
				MySqlTicketDao.pstmtGetById.close();
			}
			connection.close();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	MySqlReservationDao() throws DaoException
	{
		try
		{
			connection = MySqlDaoFactory.getConnection();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}
}