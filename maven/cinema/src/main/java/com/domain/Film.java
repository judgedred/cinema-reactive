package com.domain;


public class Film
{
	private int filmId;
	private String filmName;
	private String description;

	public int getFilmId()
	{
		return filmId;
	}

	public void setFilmId(int filmId)
	{
		this.filmId = filmId;
	}

	public String getFilmName()
	{
		return filmName;
	}

	public void setFilmName(String filmName)
	{
		this.filmName = filmName;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
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

		Film film = (Film) o;

		if(filmId != film.filmId)
		{
			return false;
		}
		if(!description.equals(film.description))
		{
			return false;
		}
		if(!filmName.equals(film.filmName))
		{
			return false;
		}

		return true;
	}

	@Override
	public int hashCode()
	{
		int result = filmId;
		result = 31 * result + filmName.hashCode();
		return result;
	}

	@Override
	public String toString()
	{
		return filmName + " " + description;
	}	
}


