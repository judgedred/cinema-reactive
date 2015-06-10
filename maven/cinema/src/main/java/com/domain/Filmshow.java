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

		Filmshow filmshow = (Filmshow) o;

		if(filmshowId != filmshow.filmshowId)
		{
			return false;
		}
		if(!dateTime.equals(filmshow.dateTime))
		{
			return false;
		}
		if(!film.equals(filmshow.film))
		{
			return false;
		}
		if(!hall.equals(filmshow.hall))
		{
			return false;
		}

		return true;
	}

	@Override
	public int hashCode()
	{
		return filmshowId;
	}

	@Override
	public String toString()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd HH:mm");
		return film.getFilmName() + " " + hall.getHallName() + " " + dateFormat.format(dateTime);
	}
}