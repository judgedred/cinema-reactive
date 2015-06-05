package com.domain;


public class User
{
	private int userId;
	private String login;
	private String password;
	private String email;

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	@Override
	public boolean equals(Object other)
	{
		if(!super.equals(other))
		{
			return false;
		}
		if(this == other)
		{
			return true;
		}
		if(other == null)
		{
			return false;
		}
		if(this.getClass() != other.getClass())
		{
			return false;
		}
		User obj = (User)other;
		if(this.userId == obj.userId && this.login == obj.login && this.password == obj.password && this.email == obj.email)
		{
			return true;
		}
		return false; 
	}
	
	@Override 
	public int hashCode()
	{
		return 26+46*userId;
	}

	@Override
	public String toString()
	{
		return login + " " + email;
	}
}