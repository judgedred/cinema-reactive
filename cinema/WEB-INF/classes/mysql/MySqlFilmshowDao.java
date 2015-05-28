package mysql;

import dao.*;
import domain.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class MySqlFilmshowDao implements FilmshowDao
{
	private Connection connection;
	private PreparedStatement pstmtCreate = null;
	private PreparedStatement pstmtUpdate = null;
	private PreparedStatement pstmtDelete = null;
	private PreparedStatement pstmtGetAll = null;
	public static PreparedStatement pstmtGetById = null;
	private PreparedStatement pstmtLastId = null;
	private ResultSet rs = null;
	private static final String sqlCreate = "Insert Into Filmshow(film_id, date_time, hall_id) Values(?, ?, ?)";
	private static final String sqlUpdate = "Update Filmshow Set film_id = ?, date_time = ?, hall_id = ? Where filmshow_id = ?";
	private static final String sqlDelete = "Delete From Filmshow Where filmshow_id = ?";
	private static final String sqlGetAll = "Select filmshow_id, film_id, date_time, hall_id From Filmshow"; 
	private static final String sqlGetById = "Select filmshow_id, film_id, date_time, hall_id From Filmshow Where filmshow_id = ?";
	private static final String sqlLastId = "Select filmshow_id, film_id, date_time, hall_id From Filmshow Where filmshow_id = last_insert_id()";

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
	public Filmshow create(Filmshow filmshow) throws DaoException
	{
		try
		{
			pstmtCreate = getPstmtCreate();
			pstmtCreate.setInt(1, filmshow.getFilm().getFilmId());
			Timestamp date = new Timestamp(filmshow.getDateTime().getTime());
			pstmtCreate.setTimestamp(2, date);
			pstmtCreate.setInt(3, filmshow.getHall().getHallId());
			pstmtCreate.executeUpdate();
			pstmtLastId = getPstmtLastId();
			rs = pstmtLastId.executeQuery();
			if(rs.next())
			{
				filmshow.setFilmshowId(rs.getInt(1));
				MySqlFilmDao filmDao = new MySqlFilmDao();
				filmshow.setFilm(filmDao.getFilmById(rs.getInt(2)));
				filmshow.setDateTime(rs.getTimestamp(3));
				MySqlHallDao hallDao = new MySqlHallDao();
				filmshow.setHall(hallDao.getHallById(rs.getInt(4)));
			}
		}
		catch(Exception e)
		{
		 	throw new DaoException(e); 
		}
		return filmshow;
	}

	@Override
	public void update(Filmshow filmshow) throws DaoException
	{
		try
		{
			pstmtUpdate = getPstmtUpdate();
			pstmtUpdate.setInt(1, filmshow.getFilm().getFilmId());
			Timestamp date = new Timestamp(filmshow.getDateTime().getTime());
			pstmtUpdate.setTimestamp(2, date);
			pstmtUpdate.setInt(3, filmshow.getHall().getHallId());
			pstmtUpdate.setInt(4, filmshow.getFilmshowId());
			pstmtUpdate.executeUpdate();
		}
		catch(Exception e)
		{	
			throw new DaoException(e);
		}
	}

	@Override
	public void delete(Filmshow filmshow) throws DaoException
	{
		try
		{
			pstmtDelete = getPstmtDelete();
			pstmtDelete.setInt(1, filmshow.getFilmshowId());
			pstmtDelete.executeUpdate();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public List<Filmshow> getFilmshowAll() throws DaoException
	{
		List<Filmshow> ls = new ArrayList<>();
		try
		{
			pstmtGetAll = getPstmtGetAll();
			rs = pstmtGetAll.executeQuery();
			MySqlFilmDao filmDao = new MySqlFilmDao();
			MySqlHallDao hallDao = new MySqlHallDao();
			while(rs.next())
			{
				Filmshow filmshow = new Filmshow();
				filmshow.setFilmshowId(rs.getInt(1));
				filmshow.setFilm(filmDao.getFilmById(rs.getInt(2)));
				filmshow.setDateTime(rs.getTimestamp(3));
				filmshow.setHall(hallDao.getHallById(rs.getInt(4)));
				ls.add(filmshow);
			}
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
		return ls;
	}

	@Override
	public Filmshow getFilmshowById(int id) throws DaoException
	{
		Filmshow filmshow = new Filmshow();
		try
		{
			pstmtGetById = getPstmtGetById();
			pstmtGetById.setInt(1, id);
			rs = pstmtGetById.executeQuery();
			if(rs.next())
			{
				filmshow.setFilmshowId(rs.getInt(1));
				MySqlFilmDao filmDao = new MySqlFilmDao();
				filmshow.setFilm(filmDao.getFilmById(rs.getInt(2)));
				filmshow.setDateTime(rs.getTimestamp(3));
				MySqlHallDao hallDao = new MySqlHallDao();
				filmshow.setHall(hallDao.getHallById(rs.getInt(4)));
			}
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
		return filmshow;
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
			if(MySqlFilmDao.pstmtGetById != null)
			{
				MySqlFilmDao.pstmtGetById.close();
			}
			if(MySqlFilmDao.pstmtGetById != null)
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

	MySqlFilmshowDao() throws DaoException
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