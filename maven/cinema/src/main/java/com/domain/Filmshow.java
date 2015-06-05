package com.domain;

import java.util.Date;
import java.text.SimpleDateFormat;


public class Filmshow
{
	private int filmshowId;
	private Date dateTime; 
	private Film film;
	private Hall hall;

	public int getFilmshowId()
	{
		return filmshowId;
	}

	public void setFilmshowId(int filmshowId)
	{
		this.filmshowId = filmshowId;
	}

	public Date getDateTime()
	{
		return dateTime;
	}
	
	public void setDateTime(Date dateTime)
	{	
		this.dateTime = dateTime;
	}

	public Film getFilm()
	{
		return film;
	}

	public void setFilm(Film film)
	{
		this.film = film;
	}

	public Hall getHall()
	{
		return hall;
	}

	public void setHall(Hall hall)
	{
		this.hall = hall;
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
		Filmshow obj = (Filmshow)other;
		if(this.filmshowId == obj.filmshowId && this.dateTime.equals(obj.dateTime) && this.film.equals(obj.film) && this.hall.equals(obj.hall))
		{
			return true;
		}
		return false; 
	}
	
	@Override 
	public int hashCode()
	{
		return 21+41*filmshowId;
	}

	@Override
	public String toString()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd HH:mm");
		return film.getFilmName() + " " + hall.getHallName() + " " + dateFormat.format(dateTime);
	}
}