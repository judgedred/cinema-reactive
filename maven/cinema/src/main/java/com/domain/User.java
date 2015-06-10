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
	public boolean equals(Object o)
	{
		if(this == o)
		{
			return true;
		}
		if(o == null || getClass() != o.getClass())
		{
			return false;
		}

		User user = (User) o;

		if(userId != user.userId)
		{
			return false;
		}
		if(!email.equals(user.email))
		{
			return false;
		}
		if(!login.equals(user.login))
		{
			return false;
		}
		if(!password.equals(user.password))
		{
			return false;
		}

		return true;
	}

	@Override
	public int hashCode()
	{
		return userId;
	}

	@Override
	public String toString()
	{
		return login + " " + email;
	}
}