package mysql;

import dao.*;
import domain.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class MySqlUserDao implements UserDao
{
	private Connection connection;
	private PreparedStatement pstmtCreate = null;
	private PreparedStatement pstmtUpdate = null;
	private PreparedStatement pstmtDelete = null;
	private PreparedStatement pstmtGetAll = null;
	public static PreparedStatement pstmtGetById = null;
	private PreparedStatement pstmtLastId = null;
	private ResultSet rs = null;
	private static final String sqlCreate = "Insert Into User(login, password, email) Values(?, ?, ?)";
	private static final String sqlUpdate = "Update User Set login = ?, password = ?, email = ?";
	private static final String sqlDelete = "Delete From User Where user_id = ?";
	private static final String sqlGetAll = "Select user_id, login, password, email From User";
	private static final String sqlGetById = "Select user_id, login, password, email From User Where user_id = ?";
	private static final String sqlLastId = "Select user_id, login, password, email From User Where user_id = last_insert_id()";

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
	public User create(User user) throws DaoException
	{
		try
		{
			pstmtCreate = getPstmtCreate();
			pstmtCreate.setString(1, user.getLogin());
			pstmtCreate.setString(2, user.getPassword());
			pstmtCreate.setString(3, user.getEmail());
			pstmtCreate.executeUpdate();
			pstmtLastId = getPstmtLastId();
			rs = pstmtLastId.executeQuery();
			if(rs.next())
			{
				user.setUserId(rs.getInt(1));
				user.setLogin(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setEmail(rs.getString(4));
			}
		}
		catch(Exception e)
		{
		 	throw new DaoException(e); 
		}
		return user;
	}

	@Override
	public void update(User user) throws DaoException
	{
		try
		{
			pstmtUpdate = getPstmtUpdate();
			pstmtUpdate.setString(1, user.getLogin());
			pstmtUpdate.setString(2, user.getPassword());
			pstmtUpdate.setString(3, user.getEmail());
			pstmtUpdate.executeUpdate();
		}
		catch(Exception e)
		{	
			throw new DaoException(e);
		}
	}

	@Override
	public void delete(User user) throws DaoException
	{
		try
		{
			pstmtDelete = getPstmtDelete();
			pstmtDelete.setInt(1, user.getUserId());
			pstmtDelete.executeUpdate();
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}

	@Override
	public List<User> getUserAll() throws DaoException
	{
		List<User> userLst = new ArrayList<>();
		try
		{
			pstmtGetAll = getPstmtGetAll();
			rs = pstmtGetAll.executeQuery();
			while(rs.next())
			{
				User user = new User();
				user.setUserId(rs.getInt(1));
				user.setLogin(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setEmail(rs.getString(4));
				userLst.add(user);
			}
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
		return userLst;
	}

	@Override
	public User getUserById(int id) throws DaoException
	{
		User user = new User();
		try
		{
			pstmtGetById = getPstmtGetById();
			pstmtGetById.setInt(1, id);
			rs = pstmtGetById.executeQuery();
			if(rs.next())
			{
				user.setUserId(rs.getInt(1));
				user.setLogin(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setEmail(rs.getString(4));
			}
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
		return user;
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

	@Override
	public User register(User user) throws DaoException
	{
		return null;
	}

	@Override
	public void changePassword(User user) throws DaoException
	{
		
	}

	MySqlUserDao() throws DaoException
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






