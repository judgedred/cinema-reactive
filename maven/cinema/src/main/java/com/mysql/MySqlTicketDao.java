package com.mysql;

import com.dao.*;
import com.domain.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class MySqlTicketDao implements TicketDao
{
	private Connection connection;
	private PreparedStatement pstmtCreate = null;
	private PreparedStatement pstmtUpdate = null;
	private PreparedStatement pstmtDelete = null;
	private PreparedStatement pstmtGetAll = null;
	public static PreparedStatement pstmtGetById = null;
	private PreparedStatement pstmtLastId = null;
	private ResultSet rs = null;
	private static final String sqlCreate = "Insert Into Ticket(filmshow_id, seat_id, price) Values(?, ?, ?)";
	private static final String sqlUpdate = "Update Ticket Set filmshow_id = ?, seat_id = ?, price = ? Where ticket_id = ?";
	private static final String sqlDelete = "Delete From Ticket Where ticket_id = ?";
	private static final String sqlGetAll = "Select ticket_id, filmshow_id, seat_id, price From Ticket"; 
	private static final String sqlGetById = "Select ticket_id, filmshow_id, seat_id, price From Ticket Where ticket_id = ?";
	private static final String sqlLastId = "Select ticket_id, filmshow_id, seat_id, price From Ticket Where ticket_id = last_insert_id()";

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
	public Ticket create(Ticket ticket) throws DaoException
	{
		try
		{
			pstmtCreate = getPstmtCreate();
			pstmtCreate.setInt(1, ticket.getFilmshow().getFilmshowId());
			pstmtCreate.setInt(2, ticket.getSeat().getSeatId());
			pstmtCreate.setFloat(3, ticket.getPrice());
			pstmtCreate.executeUpdate();
			pstmtLastId = getPstmtLastId();
			rs = pstmtLastId.executeQuery();
			if(rs.next())
			{
				ticket.setTicketId(rs.getInt(1));
				MySqlFilmshowDao filmshowDao = new MySqlFilmshowDao();
				ticket.setFilmshow(filmshowDao.getFilmshowById(rs.getInt(2)));
				MySqlSeatDao seatDao = new MySqlSeatDao();
				ticket.setSeat(seatDao.getSeatById(rs.getInt(3)));
				ticket.setPrice(rs.getFloat(4));
			}
		}
		catch(Exception e)
		{
		 	throw new DaoException(e); 
		}
		return ticket;
	}

	@Override
	public void update(Ticket ticket) throws DaoException
	{
		try
		{
			pstmtUpdate = getPstmtUpdate();
			pstmtUpdate.setInt(1, ticket.getFilmshow().getFilmshowId());
			pstmtUpdate.setInt(2, ticket.getSeat().getSeatId());
			pstmtUpdate.setFloat(3, ticket.getPrice());
			pstmtUpdate.setInt(4, ticket.getTicketId());
			pstmtUpdate.executeUpdate();
		}
		catch(Exception e)
		{	
			throw new DaoException(e);
		}
	}

	@Override
	public void delete(Ticket ticket) throws DaoException
	{
		try
		{
			pstmtDelete = getPstmtDelete();
			pstmtDelete.setInt(1, ticket.getTicketId());
			pstmtDelete.executeUpdate();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public List<Ticket> getTicketAll() throws DaoException
	{
		List<Ticket> ls = new ArrayList<>();
		try
		{
			pstmtGetAll = getPstmtGetAll();
			rs = pstmtGetAll.executeQuery();
			MySqlFilmshowDao filmshowDao = new MySqlFilmshowDao();
			MySqlSeatDao seatDao = new MySqlSeatDao();
			while(rs.next())
			{
				Ticket ticket = new Ticket();
				ticket.setTicketId(rs.getInt(1));
				ticket.setFilmshow(filmshowDao.getFilmshowById(rs.getInt(2)));
				ticket.setSeat(seatDao.getSeatById(rs.getInt(3)));
				ticket.setPrice(rs.getFloat(4));
				ls.add(ticket);
			}
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
		return ls;
	}

	@Override
	public Ticket getTicketById(int id) throws DaoException
	{
		Ticket ticket = new Ticket();
		try
		{
			pstmtGetById = getPstmtGetById();
			pstmtGetById.setInt(1, id);
			rs = pstmtGetById.executeQuery();
			if(rs.next())
			{
				ticket.setTicketId(rs.getInt(1));
				MySqlFilmshowDao filmshowDao = new MySqlFilmshowDao();
				ticket.setFilmshow(filmshowDao.getFilmshowById(rs.getInt(2)));
				MySqlSeatDao seatDao = new MySqlSeatDao();
				ticket.setSeat(seatDao.getSeatById(rs.getInt(3)));
				ticket.setPrice(rs.getFloat(4));
			}
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
		return ticket;
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
			if(MySqlFilmshowDao.pstmtGetById != null)
			{
				MySqlFilmshowDao.pstmtGetById.close();
			}
			if(MySqlSeatDao.pstmtGetById != null)
			{
				MySqlSeatDao.pstmtGetById.close();
			}
			connection.close();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	MySqlTicketDao() throws DaoException
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