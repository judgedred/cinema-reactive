package com.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "User")
public class User implements Serializable
{
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;
    @Column(name = "login")
	private String login;
    @Column(name = "password")
	private String password;
    @Column(name = "email")
	private String email;

	public Integer getUserId()
	{
		return userId;
	}

	public void setUserId(Integer userId)
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

		if(!Objects.equals(userId, user.userId))
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