package com.mysql;

import com.dao.*;
import com.domain.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class MySqlSeatDao implements SeatDao
{
	private Connection connection;
	private PreparedStatement pstmtCreate = null;
	private PreparedStatement pstmtUpdate = null;
	private PreparedStatement pstmtDelete = null;
	private PreparedStatement pstmtGetAll = null;
	public static PreparedStatement pstmtGetById = null;
	private PreparedStatement pstmtLastId = null;
	private ResultSet rs = null;
	private static final String sqlCreate = "Insert Into Seat(seat_number, row_number, hall_id) Values(?, ?, ?)";
	private static final String sqlUpdate = "Update Seat Set seat_number = ?, row_number = ?, hall_id = ? Where seat_id = ?";
	private static final String sqlDelete = "Delete From Seat Where seat_id = ?";
	private static final String sqlGetAll = "Select seat_id, seat_number, row_number, hall_id from Seat";
	private static final String sqlGetById = "Select seat_id, seat_number, row_number, hall_id From Seat Where seat_id = ?";
	private static final String sqlLastId = "Select seat_id, seat_number, row_number, hall_id From Seat Where seat_id = last_insert_id()";

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
	public Seat create(Seat seat) throws DaoException
	{
		try
		{
			pstmtCreate = getPstmtCreate();
			pstmtCreate.setInt(1, seat.getSeatNumber());
			pstmtCreate.setInt(2, seat.getRowNumber());
			pstmtCreate.setInt(3, seat.getHall().getHallId());
			pstmtCreate.executeUpdate();
			pstmtLastId = getPstmtLastId();
			rs = pstmtLastId.executeQuery();
			if(rs.next())
			{
				seat.setSeatId(rs.getInt(1));
				seat.setSeatNumber(rs.getInt(2));
				seat.setRowNumber(rs.getInt(3));
				MySqlHallDao hallDao = new MySqlHallDao();
				seat.setHall(hallDao.getHallById(rs.getInt(4)));
				return seat;
			}
			else
			{
				return null;
			}
		}
		catch(Exception e)
		{
		 	throw new DaoException(e); 
		}
	}

	@Override
	public void update(Seat seat) throws DaoException
	{
		try
		{
			pstmtUpdate = getPstmtUpdate();
			pstmtUpdate.setInt(1, seat.getSeatNumber());
			pstmtUpdate.setInt(2, seat.getRowNumber());
			pstmtUpdate.setInt(3, seat.getHall().getHallId());
			pstmtUpdate.setInt(4, seat.getSeatId());
			pstmtUpdate.executeUpdate();
		}
		catch(Exception e)
		{	
			throw new DaoException(e);
		}
	}

	@Override
	public void delete(Seat seat) throws DaoException
	{
		try
		{
			pstmtDelete = getPstmtDelete();
			pstmtDelete.setInt(1, seat.getSeatId());
			pstmtDelete.executeUpdate();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public List<Seat> getSeatAll() throws DaoException
	{
		List<Seat> ls = new ArrayList<>();
		try
		{
			pstmtGetAll = getPstmtGetAll();
			rs = pstmtGetAll.executeQuery();
			MySqlHallDao hallDao = new MySqlHallDao();
			while(rs.next())
			{
				Seat seat = new Seat();
				seat.setSeatId(rs.getInt(1));
				seat.setSeatNumber(rs.getInt(2));
				seat.setRowNumber(rs.getInt(3));
				seat.setHall(hallDao.getHallById(rs.getInt(4)));
				ls.add(seat);
			}
			return ls;
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public Seat getSeatById(int id) throws DaoException
	{
		Seat seat = new Seat();
		try
		{
			pstmtGetById = getPstmtGetById();
			pstmtGetById.setInt(1, id);
			rs = pstmtGetById.executeQuery();
			if(rs.next())
			{
				seat.setSeatId(rs.getInt(1));
				seat.setSeatNumber(rs.getInt(2));
				seat.setRowNumber(rs.getInt(3));
				MySqlHallDao hallDao = new MySqlHallDao();
				seat.setHall(hallDao.getHallById(rs.getInt(4)));
				return seat;
			}
			else
			{
				return null;
			}
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
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
			if(MySqlHallDao.pstmtGetById != null)
			{
				MySqlHallDao.pstmtGetById.close();
			}
			connection.close();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	MySqlSeatDao() throws DaoException
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