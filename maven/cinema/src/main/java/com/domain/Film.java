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
		Film obj = (Film)other;
		if(this.filmId == obj.filmId && this.filmName.equals(obj.filmName) && this.description.equals(obj.description))
		{
			return true;
		}
		return false; 
	}
	
	@Override 
	public int hashCode()
	{
		return 20+40*filmId;
	}

	@Override
	public String toString()
	{
		return filmName + " " + description;
	}	
}