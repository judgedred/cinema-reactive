package com.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Film")
public class Film implements Serializable
{
    @Id
    @Column(name = "film_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer filmId;
    @Column(name = "film_name", length = 50)	// TODO add nullable = false
	private String filmName;
    @Column(name = "description")
	private String description;

	public Integer getFilmId()
	{
		return filmId;
	}

	public void setFilmId(Integer filmId)
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

		if(!Objects.equals(filmId, film.filmId))
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


