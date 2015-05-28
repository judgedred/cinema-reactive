package mysql;

import dao.*;
import domain.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class MySqlHallDao implements HallDao
{
	private Connection connection;
	private PreparedStatement pstmtCreate = null;
	private PreparedStatement pstmtUpdate = null;
	private PreparedStatement pstmtDelete = null;
	private PreparedStatement pstmtGetAll = null;
	public static PreparedStatement pstmtGetById = null;
	private PreparedStatement pstmtLastId = null;
	private ResultSet rs = null;
	private static final String sqlCreate = "Insert Into Hall(hall_number, hall_name) Values(?, ?)";
	private static final String sqlUpdate = "Update Hall Set hall_number = ?, hall_name = ? Where hall_id = ?";
	private static final String sqlDelete = "Delete From Hall Where hall_id = ?";
	private static final String sqlGetAll = "Select hall_id, hall_number, hall_name From Hall";
	private static final String sqlGetById = "Select hall_id, hall_number, hall_name From Hall Where hall_id = ?";
	private static final String sqlLastId = "Select hall_id, hall_number, hall_name From Hall Where hall_id = last_insert_id()";

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
	public Hall create(Hall hall) throws DaoException
	{
		try
		{
			pstmtCreate = getPstmtCreate();
			pstmtCreate.setInt(1, hall.getHallNumber());
			pstmtCreate.setString(2, hall.getHallName());
			pstmtCreate.executeUpdate();
			pstmtLastId = getPstmtLastId();
			rs = pstmtLastId.executeQuery();
			if(rs.next())
			{
				hall.setHallId(rs.getInt(1));
				hall.setHallNumber(rs.getInt(2));
				hall.setHallName(rs.getString(3));
			}
		}
		catch(Exception e)
		{
		 	throw new DaoException(e); 
		}
		return hall;
	}

	@Override
	public void update(Hall hall) throws DaoException
	{
		try
		{
			pstmtUpdate = getPstmtUpdate();
			pstmtUpdate.setInt(1, hall.getHallNumber());
			pstmtUpdate.setString(2, hall.getHallName());
			pstmtUpdate.setInt(3, hall.getHallId());
			pstmtUpdate.executeUpdate();
		}
		catch(Exception e)
		{	
			throw new DaoException(e);
		}
	}

	@Override
	public void delete(Hall hall) throws DaoException
	{
		try
		{
			pstmtDelete = getPstmtDelete();
			pstmtDelete.setInt(1, hall.getHallId());
			pstmtDelete.executeUpdate();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public List<Hall> getHallAll() throws DaoException
	{
		List<Hall> ls = new ArrayList<>();
		try
		{
			pstmtGetAll = getPstmtGetAll();
			rs = pstmtGetAll.executeQuery();
			while(rs.next())
			{
				Hall hall = new Hall();
				hall.setHallId(rs.getInt(1));
				hall.setHallNumber(rs.getInt(2));
				hall.setHallName(rs.getString(3));
				ls.add(hall);
			}
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
		return ls;
	}

	@Override
	public Hall getHallById(int id) throws DaoException
	{
		Hall hall = new Hall();
		try
		{
			pstmtGetById = getPstmtGetById();
			pstmtGetById.setInt(1, id);
			rs = pstmtGetById.executeQuery();
			if(rs.next())
			{
				hall.setHallId(rs.getInt(1));
				hall.setHallNumber(rs.getInt(2));
				hall.setHallName(rs.getString(3));
			}
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
		return hall;
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
			connection.close();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	MySqlHallDao() throws DaoException
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